package com.example.cashcow_api.services.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.dtos.transaction.TransactionSummaryDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EPaymentChannel;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.ETransaction;
import com.example.cashcow_api.models.ETransactionType;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.TransactionDAO;
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
public class STransaction implements ITransaction {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Value(value = "${default.value.transaction-type.cow-purchase-type-id}")
    private Integer cowPurchaseTypeId;

    @Value(value = "${default.value.transaction-type.milk-transport-type-id}")
    private Integer milkTransportTypeId;

    @Value(value = "${default.value.payment-channel.mpesa-channel-id}")
    private Integer mpesaPaymentChannelId;

    @Value(value = "${default.value.transaction-type.transport-type-id}")
    private Integer productTransportTypeId;

    @Value(value = "${default.value.transaction-type.staff-advance-type-id}")
    private Integer staffAdvanceTypeId;

    @Value(value = "${default.value.transaction-type.staff-salary-type-id}")
    private Integer staffSalaryTypeId;

    @Value(value = "${default.value.transaction-type.system-loss-type-id}")
    private Integer systemLossTypeId;

    @Value(value = "${default.value.transaction-type.utility-type-id}")
    private Integer utilityTypeId;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private TransactionDAO transactionDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IUser sUser;

    @Autowired
    private IPaymentChannel sPaymentChannel;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private ITransactionType sTransactionType;

    @Override
    public ETransaction create(TransactionDTO transactionDTO) {
        
        ETransaction transaction = new ETransaction();
        transaction.setAmount(transactionDTO.getAmount());
        LocalDateTime transDate = transactionDTO.getCreatedOn() != null ?
            transactionDTO.getCreatedOn() : LocalDateTime.now();
        transaction.setCreatedOn(transDate);
        transaction.setReference(transactionDTO.getReference());
        transaction.setTransactionCode(transactionDTO.getTransactionCode());
        setFarm(transaction, transactionDTO.getFarmId());
        Integer paymentChannelId = transactionDTO.getPaymentChannelId() == null 
            ? mpesaPaymentChannelId : transactionDTO.getPaymentChannelId();
        setPaymentChannel(transaction, paymentChannelId);
        Integer statusId = transactionDTO.getStatusId() != null ? transactionDTO.getStatusId() : completeStatusId;
        setStatus(transaction, statusId);
        setTransactionType(transaction, transactionDTO.getTransactionTypeId());
        setUser(transaction, transactionDTO.getUserId());

        save(transaction);

        // updateCustomerBalance(transactionDTO.getCustomerId(), transactionDTO.getAmount());

        return transaction;
    }

    @Override
    public Page<ETransaction> getAllExpenses(PageDTO pageDTO) {

        List<Integer> expenseTypes = new ArrayList<>(Arrays.asList(
            cowPurchaseTypeId, milkTransportTypeId, productTransportTypeId, staffAdvanceTypeId, 
            staffSalaryTypeId, systemLossTypeId, utilityTypeId)
        );

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), "created_on"));
        return transactionDAO.findExpenses(expenseTypes, pageRequest);
    }

    @Override
    public Optional<ETransaction> getById(Integer transactionId) {
        return transactionDAO.findById(transactionId);
    }

    @Override
    public ETransaction getById(Integer transactionId, Boolean handleException) {
        Optional<ETransaction> transaction = getById(transactionId);
        if (!transaction.isPresent() && handleException) {
            throw new NotFoundException("transaction with specified id not found", "transactionId");
        }
        return transaction.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<ETransaction> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ETransaction> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ETransaction>) specFactory.generateSpecification(search, 
            specBuilder, allowableFields, "transaction");

        Specification<ETransaction> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return transactionDAO.findAll(spec, pageRequest);
    }

    @Override
    public List<TransactionSummaryDTO> getTransactionSummary(LocalDateTime startDate, LocalDateTime endDate){
        return transactionDAO.findTransactionSummary(startDate, endDate);
    }

    @Override
    public void save(ETransaction transaction) {
        transactionDAO.save(transaction);
    }

    public void setFarm(ETransaction transaction, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        transaction.setFarm(farm);
    }

    public void setPaymentChannel(ETransaction transaction, Integer paymentChannelId){
        if (paymentChannelId == null){ return; }

        EPaymentChannel paymentChannel = sPaymentChannel.getById(paymentChannelId, true);
        transaction.setPaymentChannel(paymentChannel);
    }
    
    public void setStatus(ETransaction transaction, Integer statusId){
        if (statusId == null){ return; }

        EStatus status = sStatus.getById(statusId, true);
        transaction.setStatus(status);
    }
    
    public void setTransactionType(ETransaction transaction, Integer transactionTypeId){
        if (transactionTypeId == null){ return; }

        ETransactionType transactionType = sTransactionType.getById(transactionTypeId, true);
        transaction.setTransactionType(transactionType);
    }

    public void setUser(ETransaction transaction, Integer userId) {
        if (userId == null) { return; }

        EUser user = sUser.getById(userId, true);
        transaction.setUser(user);
    }

    @Override
    public ETransaction update(Integer transactionId, TransactionDTO transactionDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateCustomerBalance(Integer customerId, BigDecimal amount){
        if (customerId != null && amount != null){
            EUser customer = sUser.getById(customerId).get();
            customer.setBalance(amount);
            sUser.save(customer);
        }
    }
    
}
