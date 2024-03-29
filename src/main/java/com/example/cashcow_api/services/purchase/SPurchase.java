package com.example.cashcow_api.services.purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.purchases.PurchaseDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EPurchase;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.PurchaseDAO;
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
    private IUser sUser;

    @Autowired
    private ITransaction sTransaction;

    @Override
    public EPurchase create(PurchaseDTO purchaseDTO) {
        
        EPurchase purchase = new EPurchase();
        LocalDateTime purchaseDate = purchaseDTO.getCreatedOn();
        purchase.setCreatedOn(purchaseDate);
        purchase.setName(purchaseDTO.getName());
        if (purchaseDTO.getQuantity() != null){
            purchase.setQuantity(purchaseDTO.getQuantity());
        }
        if (purchaseDTO.getTransportCost() != null){
            purchase.setTransportCost(purchaseDTO.getTransportCost());
            createTransaction(
                purchaseDTO.getTransportCost(),
                purchaseDate,
                mpesaPaymentChannelId, 
                transportTypeId, 
                String.format("Transport: %s", purchaseDTO.getName())
            );
        }
        purchase.setUnitPrice(purchaseDTO.getUnitPrice());
        setSupplier(purchase, purchaseDTO.getSupplierId());

        save(purchase);

        createTransaction(
            (purchaseDTO.getQuantity() * purchaseDTO.getUnitPrice()),
            purchaseDate,
            mpesaPaymentChannelId, 
            productPaymentTypeId, 
            String.format("Product: %s", purchaseDTO.getName())
        );

        return purchase;
    }

    public void createTransaction(Float amount, LocalDateTime createdOn, 
            Integer paymentChannelId, Integer transactionTypeId, String reference){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setCreatedOn(createdOn);
        transactionDTO.setPaymentChannelId(paymentChannelId);
        transactionDTO.setReference(reference);
        transactionDTO.setTransactionTypeId(transactionTypeId);
        sTransaction.create(transactionDTO);
    }

    @Override
    public Optional<EPurchase> getById(Integer purchaseId) {
        return purchaseDAO.findById(purchaseId);
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

    public void setSupplier(EPurchase purchase, Integer supplierId){

        if (supplierId == null){ return; }
        Optional<EUser> supplier = sUser.getById(supplierId);
        if (!supplier.isPresent()){
            throw new NotFoundException("supplier with specified id not found", "supplierId");
        }
        purchase.setSupplier(supplier.get());
    }
    
}
