package com.example.cashcow_api.dtos.farm;

import com.example.cashcow_api.models.EFarm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class FarmDTO {
    
    private Integer id;

    private String name;

    public FarmDTO(EFarm farm){
        setId(farm.getId());
        setName(farm.getName());
    }
}
