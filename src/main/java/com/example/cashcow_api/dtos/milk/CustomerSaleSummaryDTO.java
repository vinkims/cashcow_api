package com.example.cashcow_api.dtos.milk;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CustomerSaleSummaryDTO {
    
    private BigDecimal amount;

    private LocalDate createdOn;

    private Double quantity;

    public CustomerSaleSummaryDTO(BigDecimal amount, LocalDate createdOn, Double quantity){
        setAmount(amount);
        setCreatedOn(createdOn);
        setQuantity(quantity);
    }
}
