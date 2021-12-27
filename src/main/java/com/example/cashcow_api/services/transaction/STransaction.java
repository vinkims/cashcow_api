package com.example.cashcow_api.services.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EPaymentChannel;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.ETransaction;
import com.example.cashcow_api.models.ETransactionType;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.TransactionDAO;
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
public class STransaction implements ITransaction {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Autowired
    private TransactionDAO transactionDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IUser sUser;

    @Autowired
    private IPaymentChannel sPaymentChannel;

    @Autowired
    private IShop sShop;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private ITransactionType sTransactionType;

    @Override
    public ETransaction create(TransactionDTO transactionDTO) {
        
        ETransaction transaction = new ETransaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCreatedOn(LocalDateTime.now());
        transaction.setReference(transactionDTO.getReference());
        if (transactionDTO.getTransactionCode() != null){
            transaction.setTransactionCode(transactionDTO.getTransactionCode());
        }
        setAttendant(transaction, transactionDTO.getAttendantId());
        setCustomer(transaction, transactionDTO.getCustomerId());
        setPaymentChannel(transaction, transactionDTO.getPaymentChannelId());
        setShop(transaction, transactionDTO.getShopId());
        setTransactionType(transaction, transactionDTO.getTransactionTypeId());

        Integer statusId = transactionDTO.getStatusId() != null ? 
            transactionDTO.getStatusId() : completeStatusId;
        setStatus(transaction, statusId);

        save(transaction);

        return transaction;
    }

    @Override
    public Optional<ETransaction> getById(Integer transactionId) {
        return transactionDAO.findById(transactionId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<ETransaction> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ETransaction> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<ETransaction>) specFactory.generateSpecification(search, specBuilder, allowableFields, "transaction");
        Specification<ETransaction> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return transactionDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ETransaction transaction) {
        transactionDAO.save(transaction);
    }

    public void setAttendant(ETransaction transaction, Integer attendantId){

        if (attendantId == null) {return;}
        Optional<EUser> attendant = sUser.getById(attendantId);
        if (!attendant.isPresent()){
            throw new NotFoundException("attendant with specified id not found", "attendantId");
        }
        transaction.setAttendant(attendant.get());
    }

    public void setCustomer(ETransaction transaction, Integer customerId){

        if (customerId == null){ return; }
        Optional<EUser> customer = sUser.getById(customerId);
        if (!customer.isPresent()){
            throw new NotFoundException("customer with specified id not found", "customerId");
        }
        transaction.setCustomer(customer.get());
    }

    public void setPaymentChannel(ETransaction transaction, Integer paymentChannelId){

        if (paymentChannelId == null){ return; }
        Optional<EPaymentChannel> paymentChannel = sPaymentChannel.getById(paymentChannelId);
        if (!paymentChannel.isPresent()){
            throw new NotFoundException("payment channel with specified id not found", "paymentChannelId");
        }
        transaction.setPaymentChannel(paymentChannel.get());
    }
    
    public void setShop(ETransaction transaction, Integer shopId){

        if (shopId == null){ return; }
        Optional<EShop> shop = sShop.getById(shopId);
        if (!shop.isPresent()){
            throw new NotFoundException("shop with specified id not found", "shopId");
        }
        transaction.setShop(shop.get());
    }
    
    public void setStatus(ETransaction transaction, Integer statusId){

        if (statusId == null){ return; }
        Optional<EStatus> status = sStatus.getById(statusId);
        if (!status.isPresent()){
            throw new NotFoundException("status with specified id not found", "statusId");
        }
        transaction.setStatus(status.get());
    }
    
    public void setTransactionType(ETransaction transaction, Integer transactionTypeId){

        if (transactionTypeId == null){ return; }
        Optional<ETransactionType> transactionType = sTransactionType.getById(transactionTypeId);
        if (!transactionType.isPresent()){
            throw new NotFoundException("transaction type with specified id not found", "transactionTypeId");
        }
        transaction.setTransactionType(transactionType.get());
    }

    @Override
    public ETransaction update(ETransaction transaction, TransactionDTO transactionDTO) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
