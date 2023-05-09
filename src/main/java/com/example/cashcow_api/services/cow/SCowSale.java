package com.example.cashcow_api.services.cow;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.dtos.cow.CowSaleDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.income.IncomeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowSale;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.CowSaleDAO;
import com.example.cashcow_api.services.income.IIncome;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.services.user.IUser;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SCowSale implements ICowSale {

    @Value(value = "${default.value.income.cow-sale-type-id}")
    private Integer cowSaleIncomeTypeId;

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Value(value = "${default.value.status.sold-id}")
    private Integer soldStatusId;

    @Autowired
    private CowSaleDAO cowSaleDAO;

    @Autowired
    private ICow sCow;

    @Autowired
    private IIncome sIncome;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ECowSale create(CowSaleDTO cowSaleDTO) {

        ECowSale cowSale = new ECowSale();
        cowSale.setCreatedOn(LocalDateTime.now());
        cowSale.setSaleAmount(cowSaleDTO.getSaleAmount());
        setBuyer(cowSale, cowSaleDTO.getBuyerId());
        setCow(cowSale, cowSaleDTO.getCowId());
        Integer statusId = cowSaleDTO.getStatusId() == null ? completeStatusId : cowSaleDTO.getStatusId();
        setStatus(cowSale, statusId);

        save(cowSale);

        createIncome(cowSaleDTO, cowSale.getCow());
        return cowSale;
    }

    /**
     * Creates an income record
     */
    public void createIncome(CowSaleDTO cowSaleDTO, ECow cow) {

        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setAmount(cowSaleDTO.getSaleAmount());
        incomeDTO.setFarmId(cow.getFarm().getId());
        incomeDTO.setIncomeTypeId(cowSaleIncomeTypeId);
        if (cowSaleDTO.getReference() != null) {
            incomeDTO.setReference(cowSaleDTO.getReference());
        }

        sIncome.create(incomeDTO);
    }

    @Override
    public Optional<ECowSale> getById(Integer saleId) {
        return cowSaleDAO.findById(saleId);
    }

    @Override
    public ECowSale getById(Integer saleId, Boolean handleException) {
        Optional<ECowSale> cowSale = getById(saleId);
        if (!cowSale.isPresent() && handleException) {
            throw new NotFoundException("cow sale with specified id not found", "cowSaleId");
        }
        return cowSale.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECowSale> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECowSale> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECowSale>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowSale");

        Specification<ECowSale> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cowSaleDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowSale cowSale) {
        cowSaleDAO.save(cowSale);
    }

    public void setBuyer(ECowSale cowSale, Integer userId) {
        if (userId == null) { return; }

        EUser buyer = sUser.getById(userId, true);
        cowSale.setBuyer(buyer);
    }

    public void setCow(ECowSale cowSale, Integer cowId) {
        if (cowId == null) { return; }

        ECow cow = sCow.getById(cowId, true);
        cowSale.setCow(cow);

        updateCow(cowId);
    }

    public void setStatus(ECowSale cowSale, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        cowSale.setStatus(status);
    }

    @Override
    public ECowSale update(Integer saleId, CowSaleDTO cowSaleDTO) {

        ECowSale cowSale = getById(saleId, true);
        if (cowSaleDTO.getSaleAmount() != null) {
            cowSale.setSaleAmount(cowSaleDTO.getSaleAmount());
        }
        cowSale.setUpdatedOn(LocalDateTime.now());
        setBuyer(cowSale, cowSaleDTO.getBuyerId());
        setCow(cowSale, cowSaleDTO.getCowId());
        setStatus(cowSale, cowSaleDTO.getStatusId());

        save(cowSale);
        return cowSale;
    }

    /**
     * Updates the cow status to sold
     */
    public void updateCow(Integer cowId) {

        CowDTO cowDTO = new CowDTO();
        cowDTO.setStatusId(soldStatusId);
        try {
            sCow.update(cowId, cowDTO);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }
}

