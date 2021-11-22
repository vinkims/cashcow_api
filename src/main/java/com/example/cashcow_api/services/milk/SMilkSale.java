package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkSale;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.MilkSaleDAO;
import com.example.cashcow_api.services.shop.IShop;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.services.transaction.ITransaction;
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

    @Value(value = "${default.value.transaction-type.credit-transaction-type-id}")
    private Integer creditTransactiontypeId;

    @Value(value = "${default.value.transaction-type.milk-sale-transaction-type-id}")
    private Integer milkSaleTransactionTypeId;

    @Value(value = "${default.value.status.pending-id}")
    private Integer pendingStatusId;

    @Value(value = "${default.value.milk.price-per-litre}")
    private Float pricePerLitre;

    @Autowired
    private MilkSaleDAO milkSaleDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IShop sShop;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private ITransaction sTransaction;

    @Autowired
    private IUser sUser;

    @Override
    public EMilkSale create(MilkSaleDTO saleDTO) {

        Float amount = saleDTO.getAmount();
        
        EMilkSale milkSale = new EMilkSale();
        milkSale.setAmount(amount);
        milkSale.setCreatedOn(LocalDateTime.now());
        milkSale.setQuantity(saleDTO.getQuantity());
        setAttendant(milkSale, saleDTO.getAttendantId());
        setCustomer(milkSale, saleDTO.getCustomerId());
        setShop(milkSale, saleDTO.getShopId());

        Integer statusId = saleDTO.getStatusId() != null ? saleDTO.getStatusId() : completeStatusId;
        setStatus(milkSale, statusId);

        save(milkSale);

        // update customer balance if credit sale
        if (saleDTO.getStatusId().equals(pendingStatusId)){
            EUser customer = milkSale.getCustomer();
            customer.setBalance(-amount);
            sUser.save(customer);
        }

        Integer transactionTypeId = saleDTO.getStatusId().equals(pendingStatusId) ?
            creditTransactiontypeId : milkSaleTransactionTypeId;
        
        createTransaction(
            amount, 
            saleDTO.getAttendantId(), 
            saleDTO.getCustomerId(), 
            milkSale.getId(), 
            saleDTO.getShopId(),
            statusId,
            saleDTO.getPaymentChannelId(),
            transactionTypeId
        );

        return milkSale;
    }

    public void createTransaction(Float amount, Integer attendantId, Integer customerId, 
            Integer saleId, Integer shopId, Integer statusId, Integer paymentChannelId, Integer transactionTypeId){
        
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setAttendantId(attendantId);
        transactionDTO.setCustomerId(customerId);
        transactionDTO.setReference(saleId.toString());
        transactionDTO.setShopId(shopId);
        transactionDTO.setStatusId(statusId);
        transactionDTO.setPaymentChannelId(paymentChannelId);
        transactionDTO.setTransactionTypeId(transactionTypeId);

        sTransaction.create(transactionDTO);
    }

    @Override
    public Optional<EMilkSale> getById(Integer saleId) {
        return milkSaleDAO.findById(saleId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EMilkSale> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();
        SpecBuilder<EMilkSale> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<EMilkSale>) specFactory.generateSpecification(search, specBuilder, allowableFields, "serviceName");
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
        
        Optional<EUser> attendant = sUser.getById(attendantId);
        if (!attendant.isPresent()){
            throw new NotFoundException("attendant with specified id not found", "attendantId");
        }
        milkSale.setAttendant(attendant.get());
    }

    public void setCustomer(EMilkSale milkSale, Integer customerId){

        if (customerId == null){ return ;}
        Optional<EUser> customer = sUser.getById(customerId);
        if (!customer.isPresent()){
            throw new NotFoundException("customer with specified id not found", "customerId");
        }
        milkSale.setCustomer(customer.get());
    }

    public void setShop(EMilkSale milkSale, Integer shopId){

        Optional<EShop> shop = sShop.getById(shopId);
        if (!shop.isPresent()){
            throw new NotFoundException("shop with specified id not found", "shopId");
        }
        milkSale.setShop(shop.get());
    }

    public void setStatus(EMilkSale milkSale, Integer statusId){

        Optional<EStatus> status = sStatus.getById(statusId);
        if (!status.isPresent()){
            throw new NotFoundException("status with specified id not found", "statusId");
        }
        milkSale.setStatus(status.get());
    }

    @Override
    public EMilkSale update(EMilkSale sale, MilkSaleDTO saleDTO) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
