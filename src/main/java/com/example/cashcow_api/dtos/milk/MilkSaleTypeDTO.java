package com.example.cashcow_api.dtos.milk;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MilkSaleTypeDTO {
    
    private BigDecimal amount;

    private LocalDate createdOn;

    private Double quantity;

    private String type;

    public MilkSaleTypeDTO(BigDecimal amount, LocalDate createdOn, Double quantity, String type){
        setAmount(amount);
        setCreatedOn(createdOn);
        setQuantity(quantity);
        setType(type);
    }
}
