package com.example.cashcow_api.dtos.milk;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CustomerSaleTotalDTO {
 
    private BigDecimal amount;

    private Double quantity;

    public CustomerSaleTotalDTO(BigDecimal amount, Double quantity){
        setAmount(amount);
        setQuantity(quantity);
    }
}
