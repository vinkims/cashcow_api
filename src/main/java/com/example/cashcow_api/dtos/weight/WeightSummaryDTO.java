package com.example.cashcow_api.dtos.weight;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class WeightSummaryDTO {
    
    private LocalDate createdOn;

    private Float value;

    public WeightSummaryDTO(LocalDate createdOn, Float value){
        setCreatedOn(createdOn);
        setValue(value);
    }
}
