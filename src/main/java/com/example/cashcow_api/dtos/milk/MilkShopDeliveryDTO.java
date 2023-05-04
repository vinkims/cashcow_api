package com.example.cashcow_api.dtos.milk;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.shop.ShopBasicDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.EMilkShopDelivery;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MilkShopDeliveryDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private Float quantity;

    private ShopBasicDTO shop;

    private Integer shopId;

    private BigDecimal transportCost;

    private UserBasicDTO user;

    private Integer userId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public MilkShopDeliveryDTO(EMilkShopDelivery delivery){
        setCreatedOn(delivery.getCreatedOn());
        setId(delivery.getId());
        setQuantity(delivery.getQuantity());
        setShop(new ShopBasicDTO(delivery.getShop()));
        if (delivery.getStatus() != null) {
            setStatus(new Status(delivery.getStatus()));
        }
        setUpdatedOn(delivery.getUpdatedOn());
        setUser(new UserBasicDTO(delivery.getUser()));
    }
}
