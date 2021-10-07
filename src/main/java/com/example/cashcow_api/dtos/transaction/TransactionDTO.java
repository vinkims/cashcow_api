package com.example.cashcow_api.dtos.transaction;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.shop.ShopDTO;
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
    
    private Float amount;

    private UserBasicDTO attendant;

    private Integer attendantId;

    private LocalDateTime createdOn;

    private UserBasicDTO customer;

    private Integer customerId;

    private Integer id;

    private String paymentChannel;

    private Integer paymentChannelId;

    private String reference;

    private ShopDTO shop;

    private Integer shopId;

    private String status;

    private Integer statusId;

    private String transactionType;

    private Integer transactionTypeId;

    public TransactionDTO(ETransaction transaction){
        setAmount(transaction.getAmount());
        setAttendant(new UserBasicDTO(transaction.getAttendant()));
        setCreatedOn(transaction.getCreatedOn());
        setCustomer(new UserBasicDTO(transaction.getCustomer()));
        setId(transaction.getId());
        setPaymentChannel(transaction.getPaymentChannel().getName());
        setReference(transaction.getReference());
        setShop(new ShopDTO(transaction.getShop()));
        setStatus(transaction.getStatus().getName());
        setTransactionType(transaction.getTransactionType().getName());
    }
}
