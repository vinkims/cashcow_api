package com.example.cashcow_api.dtos.milk;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MilkConsumptionSummaryDTO {

    private Float amount;
    
    private String category;

    private LocalDate createdOn;

    private BigDecimal unitCost;

    private Float quantity;

    private String session;

    public MilkConsumptionSummaryDTO(BigDecimal unitCost, String category, LocalDate createdOn, Float quantity, String session, Float amount){
        setAmount(amount);
        setCategory(category);
        setCreatedOn(createdOn);
        setQuantity(quantity);
        setSession(session);
        setUnitCost(unitCost);
    }
}
