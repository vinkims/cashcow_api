package com.example.cashcow_api.dtos.milk;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.dtos.user.UserDTO;
import com.example.cashcow_api.models.EMilkSale;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MilkSaleDTO {
    
    private Float amount;

    private UserDTO attendant;

    private Integer attendantId;

    private LocalDateTime createdOn;

    private UserDTO customer;

    private Integer customerId;

    private Integer paymentChannelId;

    private Integer saleId;

    private ShopDTO shop;

    private Integer shopId;

    private String status;

    private Integer statusId;

    private Float quantity;

    public MilkSaleDTO(EMilkSale sale){
        setAmount(sale.getAmount());
        setAttendant(new UserDTO(sale.getAttendant()));
        setCreatedOn(sale.getCreatedOn());
        setCustomer(new UserDTO(sale.getCustomer()));
        setSaleId(sale.getId());
        setShop(new ShopDTO(sale.getShop()));
        setStatus(sale.getStatus().getName());
        setQuantity(sale.getQuantity());
    }
}
