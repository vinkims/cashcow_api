package com.example.cashcow_api.dtos.purchases;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.EPurchase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseDTO {
    
    private LocalDateTime createdOn;

    private Integer id;

    private String name;

    private UserBasicDTO supplier;

    private Float quantity;

    private Integer supplierId;

    private Float transportCost;

    private Float unitPrice;

    public PurchaseDTO(EPurchase purchase){
        setCreatedOn(purchase.getCreatedOn());
        setId(purchase.getId());
        setName(purchase.getName());
        setQuantity(purchase.getQuantity());
        if (purchase.getSupplier() != null){
            setSupplier(new UserBasicDTO(purchase.getSupplier()));
        }
        setTransportCost(purchase.getTransportCost());
    }
}
