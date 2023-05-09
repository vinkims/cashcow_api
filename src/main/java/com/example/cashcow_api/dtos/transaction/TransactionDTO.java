package com.example.cashcow_api.dtos.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.ETransaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String transactionCode;
    
    private BigDecimal amount;

    private String reference;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private UserBasicDTO user;

    private Integer userId;

    @JsonIgnoreProperties("description")
    private TransactionTypeDTO transactionType;

    private Integer transactionTypeId;

    @JsonIgnoreProperties("description")
    private PaymentChannelDTO paymentChannel;

    private Integer paymentChannelId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public TransactionDTO(ETransaction transaction){
        setAmount(transaction.getAmount());
        setCreatedOn(transaction.getCreatedOn());
        if (transaction.getFarm() != null) {
            setFarm(new FarmDTO(transaction.getFarm()));
        }
        setId(transaction.getId());
        if (transaction.getPaymentChannel() != null) {
            setPaymentChannel(new PaymentChannelDTO(transaction.getPaymentChannel()));
        }
        setReference(transaction.getReference());
        setStatus(new StatusDTO(transaction.getStatus()));
        setTransactionCode(transaction.getTransactionCode());
        if (transaction.getTransactionType() != null) {
            setTransactionType(new TransactionTypeDTO(transaction.getTransactionType()));
        }
        setUpdatedOn(transaction.getUpdatedOn());
        if (transaction.getUser() != null) {
            setUser(new UserBasicDTO(transaction.getUser()));
        }
    }
}
