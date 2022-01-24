package com.example.cashcow_api.dtos.shop;

import com.example.cashcow_api.models.EShop;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class ShopBasicDTO {
    
    private Integer id;

    private String name;

    public ShopBasicDTO(EShop shop){

        setId(shop.getId());
        setName(shop.getName());
    }
}
