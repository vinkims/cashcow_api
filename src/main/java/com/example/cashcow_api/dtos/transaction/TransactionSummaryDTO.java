package com.example.cashcow_api.dtos.transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionSummaryDTO {
    
    private Double amount;

    private String transactionType;

    public TransactionSummaryDTO(Double amount, String transactionType){
        setAmount(amount);
        setTransactionType(transactionType);
    }
}
