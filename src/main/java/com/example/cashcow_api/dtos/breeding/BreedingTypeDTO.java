package com.example.cashcow_api.dtos.breeding;

import com.example.cashcow_api.models.EBreedingType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class BreedingTypeDTO {
    
    private Integer id;

    private String name;

    private String description;

    public BreedingTypeDTO(EBreedingType breedingType) {
        setDescription(breedingType.getDescription());
        setId(breedingType.getId());
        setName(breedingType.getName());
    }
}
