package com.example.cashcow_api.dtos.sales;

import com.example.cashcow_api.models.ESaleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class SaleTypeDTO {
    
    private Integer id;

    private String name;

    private String description;

    public SaleTypeDTO(ESaleType saleType) {
        setDescription(saleType.getDescription());
        setId(saleType.getId());
        setName(saleType.getName());
    }
}
