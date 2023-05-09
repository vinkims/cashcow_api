package com.example.cashcow_api.dtos.transaction;

import com.example.cashcow_api.models.ETransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class TransactionTypeDTO {
    
    private Integer id;

    private String name;

    private String description;

    public TransactionTypeDTO(ETransactionType transactionType) {
        setDescription(transactionType.getDescription());
        setId(transactionType.getId());
        setName(transactionType.getName());
    }
}
