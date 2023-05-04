package com.example.cashcow_api.dtos.shop;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsShopNameValid;
import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
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

    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @NotBlank
    @IsShopNameValid
    private String name;
    
    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private String location;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public ShopDTO(EShop shop){
        setCreatedOn(shop.getCreatedOn());
        setFarm(new FarmDTO(shop.getFarm()));
        setId(shop.getId());
        setName(shop.getName());
        setLocation(shop.getLocation());
        if (shop.getStatus() != null) {
            setStatus(new StatusDTO(shop.getStatus()));
        }
        setUpdatedOn(shop.getUpdatedOn());
    }
}
