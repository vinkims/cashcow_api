package com.example.cashcow_api.dtos.transaction;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionSummaryDTO {
    
    private BigDecimal amount;

    private String transactionType;

    public TransactionSummaryDTO(BigDecimal amount, String transactionType){
        setAmount(amount);
        setTransactionType(transactionType);
    }
}
