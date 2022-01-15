package com.example.cashcow_api.dtos.milk;

import com.example.cashcow_api.models.EMilkConsumptionCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class MilkConsumptionCategoryDTO {
    
    private String description;

    private Integer id;

    private String name;

    public MilkConsumptionCategoryDTO(EMilkConsumptionCategory category){
        if (category.getDescription() != null){
            setDescription(category.getDescription());
        }
        setId(category.getId());
        setName(category.getName());
    }
}
