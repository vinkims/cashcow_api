package com.example.cashcow_api.dtos.shop;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsShopNameValid;
import com.example.cashcow_api.dtos.farm.FarmDTO;
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
public class ShopDTO {
    
    private FarmDTO farm;

    private Integer farmId;

    private Integer shopId;

    @NotBlank
    @IsShopNameValid
    private String name;

    private String location;

    public ShopDTO(EShop shop){
        setFarm(new FarmDTO(shop.getFarm()));
        this.setShopId(shop.getId());
        this.setName(shop.getName());
        this.setLocation(shop.getLocation());
    }
}
