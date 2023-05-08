package com.example.cashcow_api.services.cow;

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

import com.example.cashcow_api.dtos.cow.CowPurchaseDTO;
import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowPurchase;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.CowPurchaseDAO;
import com.example.cashcow_api.services.expense.IExpense;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SCowPurchase implements ICowPurchase {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Value(value = "${default.value.expense.cow-purchase-type-id}")
    private Integer cowPurchaseExpenseTypeId;

    @Autowired
    private CowPurchaseDAO cowPurchaseDAO;

    @Autowired
    private ICow sCow;

    @Autowired
    private IExpense sExpense;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ECowPurchase create(CowPurchaseDTO cowPurchaseDTO) {

        ECowPurchase cowPurchase = new ECowPurchase();
        cowPurchase.setCreatedOn(LocalDateTime.now());
        cowPurchase.setPurchaseAmount(cowPurchaseDTO.getPurchaseAmount());
        cowPurchase.setPurchaseLocation(cowPurchaseDTO.getPurchaseLocation());
        setCow(cowPurchase, cowPurchaseDTO.getCowId());
        Integer statusId = cowPurchaseDTO.getStatusId() == null ? completeStatusId : cowPurchaseDTO.getStatusId();
        setStatus(cowPurchase, statusId);

        save(cowPurchase);

        createExpense(
            cowPurchaseDTO, 
            cowPurchase.getCow(), 
            String.format("Cow purchase id: %s", cowPurchase.getId())
        );
        return cowPurchase;
    }

    /**
     * Creates an expense record
     */
    public void createExpense(CowPurchaseDTO cowPurchaseDTO, ECow cow, String description) {

        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setAmount(cowPurchaseDTO.getPurchaseAmount());
        expenseDTO.setCowId(cow.getId());
        expenseDTO.setDescription(description);
        expenseDTO.setExpenseTypeId(cowPurchaseExpenseTypeId);
        expenseDTO.setFarmId(cow.getFarm().getId());

        sExpense.create(expenseDTO);
    }

    @Override
    public Optional<ECowPurchase> getById(Integer id) {
        return cowPurchaseDAO.findById(id);
    }

    @Override
    public ECowPurchase getById(Integer id, Boolean handleException) {
        Optional<ECowPurchase> purchase = getById(id);
        if (!purchase.isPresent() && handleException) {
            throw new NotFoundException("cow purchase with specified id not found", "cowPurchaseId");
        }
        return purchase.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECowPurchase> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECowPurchase> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECowPurchase>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowPurchase");

        Specification<ECowPurchase> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cowPurchaseDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowPurchase cowPurchase) {
        cowPurchaseDAO.save(cowPurchase);
    }

    public void setCow(ECowPurchase cowPurchase, Integer cowId) {
        if (cowId == null) { return; }

        ECow cow = sCow.getById(cowId, true);
        cowPurchase.setCow(cow);
    }

    public void setStatus(ECowPurchase cowPurchase, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        cowPurchase.setStatus(status);
    }

    @Override
    public ECowPurchase update(Integer id, CowPurchaseDTO cowPurchaseDTO) {

        ECowPurchase cowPurchase = getById(id, true);
        cowPurchase.setPurchaseAmount(cowPurchaseDTO.getPurchaseAmount());
        cowPurchase.setPurchaseLocation(cowPurchaseDTO.getPurchaseLocation());
        cowPurchase.setUpdatedOn(LocalDateTime.now());
        setCow(cowPurchase, cowPurchaseDTO.getCowId());
        setStatus(cowPurchase, cowPurchaseDTO.getStatusId());

        save(cowPurchase);
        return cowPurchase;
    }
    
}
