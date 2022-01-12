package com.example.cashcow_api.dtos.milk;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.shop.ShopBasicDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.EMilkDelivery;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MilkDeliveryDTO {
    
    private LocalDateTime createdOn;

    private Integer id;

    private Float quantity;

    private UserBasicDTO user;

    private Integer userId;

    private ShopBasicDTO shop;

    private Integer shopId;

    public MilkDeliveryDTO(EMilkDelivery delivery){
        setCreatedOn(delivery.getCreatedOn());
        setId(delivery.getId());
        setQuantity(delivery.getQuantity());
        setUser(new UserBasicDTO(delivery.getUser()));
        setShop(new ShopBasicDTO(delivery.getShop()));
    }
}
