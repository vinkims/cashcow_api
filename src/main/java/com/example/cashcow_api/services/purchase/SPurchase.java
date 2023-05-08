package com.example.cashcow_api.services.purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.purchases.PurchaseDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EPurchase;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.PurchaseDAO;
import com.example.cashcow_api.services.expense.IExpense;
import com.example.cashcow_api.services.farm.IFarm;
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
public class SPurchase implements IPurchase {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Value(value = "${default.value.expense.product-purchase-type-id}")
    private Integer purchaseExpenseTypeId;

    @Value(value = "${default.value.expense.transport-type-id}")
    private Integer transportExpenseTypeId;

    @Autowired
    private PurchaseDAO purchaseDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IExpense sExpense;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Override
    public EPurchase create(PurchaseDTO purchaseDTO) {
        
        EPurchase purchase = new EPurchase();
        LocalDateTime purchaseDate = purchaseDTO.getCreatedOn();
        purchase.setCreatedOn(purchaseDate);
        setFarm(purchase, purchaseDTO.getFarmId());
        purchase.setName(purchaseDTO.getName());
        purchase.setQuantity(purchaseDTO.getQuantity());
        Integer statusId = purchaseDTO.getStatusId() == null ? completeStatusId : purchaseDTO.getStatusId();
        setStatus(purchase, statusId);
        purchase.setUnitCost(purchaseDTO.getUnitCost());
        setSupplier(purchase, purchaseDTO.getSupplierId());

        save(purchase);

        BigDecimal quantity = purchaseDTO.getQuantity() != null 
            ? new BigDecimal("1") 
            : new BigDecimal(purchaseDTO.getQuantity());
        BigDecimal totalCost = quantity.multiply(purchaseDTO.getUnitCost());

        createExpense(
            totalCost, 
            purchaseExpenseTypeId, 
            purchase.getFarm().getId(), 
            String.format("Purchase id: %s", purchase.getId())
        );

        if (purchaseDTO.getTransportCost() != null){
            createExpense(
                purchaseDTO.getTransportCost(), 
                transportExpenseTypeId, 
                purchase.getFarm().getId(), 
                String.format("Purchase id: %s transport cost", purchase.getId())
            );
        }

        return purchase;
    }

    /**
     * Creates an expense
     */
    public void createExpense(BigDecimal amount, Integer expenseTypeId, Integer farmId, String description) {

        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setAmount(amount);
        expenseDTO.setDescription(description);
        expenseDTO.setExpenseTypeId(expenseTypeId);
        expenseDTO.setFarmId(farmId);

        sExpense.create(expenseDTO);
    }

    @Override
    public Optional<EPurchase> getById(Integer purchaseId) {
        return purchaseDAO.findById(purchaseId);
    }

    @Override
    public EPurchase getById(Integer purchaseId, Boolean handleException) {
        Optional<EPurchase> purchase = getById(purchaseId);
        if (!purchase.isPresent() && handleException) {
            throw new NotFoundException("purchase with specified id not found", "purchaseId");
        }
        return purchase.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EPurchase> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EPurchase> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<EPurchase>) specFactory.generateSpecification(search, specBuilder, allowableFields, "purchase");
        Specification<EPurchase> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return purchaseDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EPurchase purchase) {
        purchaseDAO.save(purchase);
    }

    public void setFarm(EPurchase purchase, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        purchase.setFarm(farm);
    }

    public void setStatus(EPurchase purchase, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        purchase.setStatus(status);
    }

    public void setSupplier(EPurchase purchase, Integer supplierId){
        if (supplierId == null){ return; }

        EUser supplier = sUser.getById(supplierId, true);
        purchase.setSupplier(supplier);
    }

    @Override
    public EPurchase update(Integer purchaseId, PurchaseDTO purchaseDTO) {

        EPurchase purchase = getById(purchaseId, true);
        if (purchaseDTO.getName() != null) {
            purchase.setName(purchaseDTO.getName());
        }
        if (purchaseDTO.getQuantity() != null) {
            purchase.setQuantity(purchaseDTO.getQuantity());
        }
        if (purchaseDTO.getUnitCost() != null) {
            purchase.setUnitCost(purchaseDTO.getUnitCost());
        }
        purchase.setUpdatedOn(LocalDateTime.now());

        setFarm(purchase, purchaseDTO.getFarmId());
        setStatus(purchase, purchaseDTO.getStatusId());
        setSupplier(purchase, purchaseDTO.getSupplierId());

        save(purchase);
        return purchase;
    }

    // TODO: Complete transaction and expense creation
    
}
