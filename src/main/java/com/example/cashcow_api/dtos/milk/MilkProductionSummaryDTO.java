package com.example.cashcow_api.dtos.milk;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class MilkProductionSummaryDTO {
    
    private LocalDate createdOn;

    private Double quantity;

    public MilkProductionSummaryDTO(LocalDate createdOn, Double quantity){
        setCreatedOn(createdOn);
        setQuantity(quantity);
    }
}
