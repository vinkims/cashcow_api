package com.example.cashcow_api.services.purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.purchases.PurchaseDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EPurchase;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.PurchaseDAO;
import com.example.cashcow_api.services.farm.IFarm;
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
public class SPurchase implements IPurchase {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Value(value = "${default.value.payment-channel.mpesa-channel-id}")
    private Integer mpesaPaymentChannelId;

    @Value(value = "${default.value.transaction-type.product-payment-type-id}")
    private Integer productPaymentTypeId;

    @Value(value = "${default.value.transaction-type.transport-type-id}")
    private Integer transportTypeId;

    @Autowired
    private PurchaseDAO purchaseDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Autowired
    private ITransaction sTransaction;

    @Override
    public EPurchase create(PurchaseDTO purchaseDTO) {
        
        EPurchase purchase = new EPurchase();
        LocalDateTime purchaseDate = purchaseDTO.getCreatedOn();
        purchase.setCreatedOn(purchaseDate);
        setFarm(purchase, purchaseDTO.getFarmId());
        purchase.setName(purchaseDTO.getName());
        if (purchaseDTO.getQuantity() != null){
            purchase.setQuantity(purchaseDTO.getQuantity());
        }
        Integer statusId = purchaseDTO.getStatusId() == null ? completeStatusId : purchaseDTO.getStatusId();
        setStatus(purchase, statusId);
        if (purchaseDTO.getTransportCost() != null){
            // createTransaction(
            //     purchaseDTO.getTransportCost(),
            //     purchaseDate,
            //     mpesaPaymentChannelId, 
            //     transportTypeId, 
            //     String.format("Transport: %s", purchaseDTO.getName())
            // );
        }
        purchase.setUnitCost(purchaseDTO.getUnitCost());
        setSupplier(purchase, purchaseDTO.getSupplierId());

        save(purchase);

        // createTransaction(
        //     (purchaseDTO.getQuantity() * purchaseDTO.getUnitPrice()),
        //     purchaseDate,
        //     mpesaPaymentChannelId, 
        //     productPaymentTypeId, 
        //     String.format("Product: %s", purchaseDTO.getName())
        // );

        return purchase;
    }

    // public void createTransaction(Float amount, LocalDateTime createdOn, 
    //         Integer paymentChannelId, Integer transactionTypeId, String reference){
    //     TransactionDTO transactionDTO = new TransactionDTO();
    //     transactionDTO.setAmount(amount);
    //     transactionDTO.setCreatedOn(createdOn);
    //     transactionDTO.setPaymentChannelId(paymentChannelId);
    //     transactionDTO.setReference(reference);
    //     transactionDTO.setTransactionTypeId(transactionTypeId);
    //     sTransaction.create(transactionDTO);
    // }

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
