package com.example.cashcow_api.dtos.milk;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class MilkSaleTotalDTO {
    
    private Double amount;

    private LocalDate createdOn;

    private Double quantity;

    public MilkSaleTotalDTO(Double amount, LocalDate createdOn, Double quantity){
        setAmount(amount);
        setCreatedOn(createdOn);
        setQuantity(quantity);
    }
}
