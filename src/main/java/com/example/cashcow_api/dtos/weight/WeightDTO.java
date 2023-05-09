package com.example.cashcow_api.dtos.weight;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.cow.CowBasicDTO;
import com.example.cashcow_api.models.ECowWeight;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeightDTO {
    
    private CowBasicDTO cow;

    private Integer cowId;

    private LocalDateTime createdOn;

    private Integer id;

    private Float weight;

    public WeightDTO(ECowWeight weight){
        setCow(new CowBasicDTO(weight.getCow()));
        setCreatedOn(weight.getCreatedOn());
        setId(weight.getId());
        setWeight(weight.getWeight());
    }
}
