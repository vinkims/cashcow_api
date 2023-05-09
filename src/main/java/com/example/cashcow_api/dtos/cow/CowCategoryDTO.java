package com.example.cashcow_api.dtos.cow;

import com.example.cashcow_api.models.ECowCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowCategoryDTO {
    
    private Integer id;

    private String name;

    private String description;

    public CowCategoryDTO(ECowCategory category) {
        setDescription(category.getDescription());
        setId(category.getId());
        setName(category.getName());
    }
}
