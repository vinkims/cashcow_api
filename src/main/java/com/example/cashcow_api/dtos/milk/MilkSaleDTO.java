package com.example.cashcow_api.dtos.milk;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.sales.SaleTypeDTO;
import com.example.cashcow_api.dtos.shop.ShopBasicDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
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

    private Integer id;
    
    private BigDecimal amount;

    private UserBasicDTO attendant;

    private Integer attendantId;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private UserBasicDTO customer;

    private Integer customerId;

    private Integer paymentChannelId;

    private BigDecimal unitCost;

    private Float quantity;

    @JsonIgnoreProperties("description")
    private SaleTypeDTO saleType;

    private Integer saleTypeId;

    private ShopBasicDTO shop;

    private Integer shopId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    private String transactionCode;

    public MilkSaleDTO(EMilkSale sale){
        setAmount(sale.getAmount());
        setAttendant(new UserBasicDTO(sale.getAttendant()));
        setCreatedOn(sale.getCreatedOn());
        if (sale.getCustomer() != null){
            setCustomer(new UserBasicDTO(sale.getCustomer()));
        }
        setId(sale.getId());
        if (sale.getSaleType() != null){
            setSaleType(new SaleTypeDTO(sale.getSaleType()));
        }
        setShop(new ShopBasicDTO(sale.getShop()));
        setStatus(new StatusDTO(sale.getStatus()));
        setQuantity(sale.getQuantity());
        setUpdatedOn(sale.getUpdatedOn());
    }
}
