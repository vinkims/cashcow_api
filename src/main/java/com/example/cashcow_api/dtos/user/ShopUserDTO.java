package com.example.cashcow_api.dtos.user;

import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.models.EShopUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ShopUserDTO {
    
    private ShopDTO shop;

    private Integer shopId;

    public ShopUserDTO(EShopUser shopUser) {
        setShop(new ShopDTO(shopUser.getShop()));
    }
}
