package com.example.cashcow_api.dtos.milk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class DailyCowProductionDTO {
    
    private Float quantity;

    private String session;

    public DailyCowProductionDTO(Float quantity, String session){
        setQuantity(quantity);
        setSession(session);
    }
}
