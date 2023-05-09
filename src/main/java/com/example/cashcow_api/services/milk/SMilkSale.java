package com.example.cashcow_api.services.milk;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.income.IncomeDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleTotalDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTypeDTO;
import com.example.cashcow_api.exceptions.InvalidInputException;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkSale;
import com.example.cashcow_api.models.ESaleType;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.MilkSaleDAO;
import com.example.cashcow_api.services.income.IIncome;
import com.example.cashcow_api.services.sale.ISaleType;
import com.example.cashcow_api.services.shop.IShop;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.services.user.IUser;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SMilkSale implements IMilkSale {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Value(value = "${default.value.sale.credit-sale-type-id}")
    private Integer creditSaleTypeId;

    @Value(value = "${default.value.income.milk-sale-type-id}")
    private Integer milkSaleIncomeTypeId;

    @Autowired
    private IIncome sIncome;

    @Autowired
    private MilkSaleDAO milkSaleDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private ISaleType sSaleType;

    @Autowired
    private IShop sShop;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Override
    public EMilkSale create(MilkSaleDTO saleDTO) {

        BigDecimal amount = saleDTO.getAmount();
        Integer customerId = saleDTO.getCustomerId();
        BigDecimal litrePrice = saleDTO.getUnitCost();

        Float quantity = saleDTO.getQuantity();
        BigDecimal expectedAmount = litrePrice.multiply(new BigDecimal(quantity));

        if (!amount.equals(expectedAmount) && customerId == null){
            throw new InvalidInputException("Please select customer", "customer");
        }

        EMilkSale milkSale = new EMilkSale();
        milkSale.setAmount(amount);
        milkSale.setCreatedOn(saleDTO.getCreatedOn());
        milkSale.setQuantity(quantity);
        setAttendant(milkSale, saleDTO.getAttendantId());
        setCustomer(milkSale, customerId);
        setSaleType(milkSale, saleDTO.getSaleTypeId());
        setShop(milkSale, saleDTO.getShopId());

        Integer statusId = saleDTO.getStatusId() != null ? saleDTO.getStatusId() : completeStatusId;
        setStatus(milkSale, statusId);

        save(milkSale);

        // update customer balance
        if (saleDTO.getSaleTypeId().equals(creditSaleTypeId)){
            EUser milkCustomer = milkSale.getCustomer();
            milkCustomer.setBalance(expectedAmount.negate());
            sUser.save(milkCustomer);
        }

        return milkSale;
    }

    public void createIncome(BigDecimal amount, Integer farmId, Integer customerId) {

        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setAmount(amount);
        incomeDTO.setFarmId(farmId);
        incomeDTO.setIncomeTypeId(milkSaleIncomeTypeId);
        if (customerId != null) {
            incomeDTO.setReference(String.format("Customer id: %s", customerId));
        }

        sIncome.create(incomeDTO);
    }

    @Override
    public List<CustomerSaleSummaryDTO> getCustomerSaleSummary(LocalDateTime startDate, LocalDateTime endDate, Integer customerId){
        return milkSaleDAO.findCustomerSaleSummary(startDate, endDate, customerId);
    }

    @Override
    public List<CustomerSaleTotalDTO> getCustomerSaleTotal(LocalDateTime startDate, LocalDateTime endDate, Integer customerId){
        return milkSaleDAO.findCustomerSaleTotal(startDate, endDate, customerId);
    }

    @Override
    public Optional<EMilkSale> getById(Integer saleId) {
        return milkSaleDAO.findById(saleId);
    }

    @Override
    public EMilkSale getById(Integer saleId, Boolean handleException) {
        Optional<EMilkSale> milkSale = getById(saleId);
        if (!milkSale.isPresent() && handleException) {
            throw new NotFoundException("milk sale with specified id not found", "milkSaleId");
        }
        return milkSale.get();
    }

    @Override
    public List<MilkSaleSummaryDTO> getMilkSaleSummary(LocalDateTime startDate, LocalDateTime endDate){
        return milkSaleDAO.findMilkSaleSummary(startDate, endDate);
    }

    @Override
    public List<MilkSaleSummaryDTO> getMilkSaleSummaryByShop(LocalDateTime startDate, LocalDateTime endDate, Integer shopId){
        return milkSaleDAO.findMilkSaleSummaryByShop(startDate, endDate, shopId);
    }

    @Override
    public List<MilkSaleTypeDTO> getMilkSaleTypeSummary(LocalDateTime startDate, LocalDateTime endDate, Integer shopId){
        return milkSaleDAO.findMilkSaleTypeSummary(startDate, endDate, shopId);
    }

    @Override
    public List<MilkSaleTotalDTO> getMilkSaleTotal(LocalDateTime startDate, LocalDateTime endDate){
        return milkSaleDAO.findMilkSaleTotal(startDate, endDate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EMilkSale> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EMilkSale> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EMilkSale>) specFactory.generateSpecification(search, 
            specBuilder, allowableFields, "serviceName");

        Specification<EMilkSale> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));
        
        return milkSaleDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EMilkSale sale) {
        milkSaleDAO.save(sale);
        
    }

    public void setAttendant(EMilkSale milkSale, Integer attendantId){
        if (attendantId == null) { return; }
        
        EUser attendant = sUser.getById(attendantId, true);
        milkSale.setAttendant(attendant);
    }

    public void setCustomer(EMilkSale milkSale, Integer customerId){
        if (customerId == null){ return ;}

        EUser customer = sUser.getById(customerId, true);
        milkSale.setCustomer(customer);
    }

    public void setSaleType(EMilkSale milkSale, Integer saleTypeId){
        if (saleTypeId == null){ return; }

        ESaleType saleType = sSaleType.getById(saleTypeId, true);
        milkSale.setSaleType(saleType);
    }

    public void setShop(EMilkSale milkSale, Integer shopId){
        if (shopId == null) { return; }

        EShop shop = sShop.getById(shopId, true);
        milkSale.setShop(shop);
    }

    public void setStatus(EMilkSale milkSale, Integer statusId){
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        milkSale.setStatus(status);
    }

    @Override
    public EMilkSale update(Integer saleId, MilkSaleDTO saleDTO) {
        
        EMilkSale milkSale = getById(saleId, true);
        if (saleDTO.getAmount() != null) {
            milkSale.setAmount(saleDTO.getAmount());
        }
        if (saleDTO.getQuantity() != null) {
            milkSale.setQuantity(saleDTO.getQuantity());
        }
        milkSale.setUpdatedOn(LocalDateTime.now());
        setAttendant(milkSale, saleDTO.getAttendantId());
        setCustomer(milkSale, saleDTO.getCustomerId());
        setSaleType(milkSale, saleDTO.getSaleTypeId());
        setShop(milkSale, saleDTO.getShopId());
        setStatus(milkSale, saleDTO.getStatusId());

        save(milkSale);
        return milkSale;

        // Modify transaction and income record
    }
    
}
