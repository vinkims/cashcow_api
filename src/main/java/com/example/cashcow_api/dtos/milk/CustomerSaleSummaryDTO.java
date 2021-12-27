package com.example.cashcow_api.dtos.milk;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CustomerSaleSummaryDTO {
    
    private Float amount;

    private LocalDate createdOn;

    private Float quantity;

    public CustomerSaleSummaryDTO(Float amount, LocalDate createdOn, Float quantity){
        setAmount(amount);
        setCreatedOn(createdOn);
        setQuantity(quantity);
    }
}
