package com.example.cashcow_api.dtos.transaction;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeTransactionDTO {
    
    private Float amount;

    private LocalDate createdOn;

    private String transactionType;

    private String userName;

    public EmployeeTransactionDTO(Float amount, LocalDate createdOn, String transactionType, String userName){
        setAmount(amount);
        setCreatedOn(createdOn);
        setTransactionType(transactionType);
        setUserName(userName);
    }
}
