package com.example.cashcow_api.dtos.milk;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MilkConsumptionSummaryDTO {

    private Float amount;
    
    private String category;

    private LocalDate createdOn;

    private Float litrePrice;

    private Float quantity;

    private String session;

    public MilkConsumptionSummaryDTO(Float litrePrice, String category, LocalDate createdOn, Float quantity, String session, Float amount){
        setAmount(amount);
        setCategory(category);
        setCreatedOn(createdOn);
        setLitrePrice(litrePrice);
        setQuantity(quantity);
        setSession(session);
    }
}
