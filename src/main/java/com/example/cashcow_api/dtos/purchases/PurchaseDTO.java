package com.example.cashcow_api.dtos.purchases;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
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

    private Integer id;
    
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private String name;

    private Float quantity;

    private BigDecimal unitCost;

    private BigDecimal transportCost;

    private UserBasicDTO supplier;

    private Integer supplierId;

    @JsonIgnoreProperties("value")
    private StatusDTO status;

    private Integer statusId;

    public PurchaseDTO(EPurchase purchase){
        setCreatedOn(purchase.getCreatedOn());
        if (purchase.getFarm() != null) {
            setFarm(new FarmDTO(purchase.getFarm()));
        }
        setId(purchase.getId());
        setName(purchase.getName());
        setQuantity(purchase.getQuantity());
        if (purchase.getSupplier() != null){
            setSupplier(new UserBasicDTO(purchase.getSupplier()));
        }
        if (purchase.getStatus() != null) {
            setStatus(new StatusDTO(purchase.getStatus()));
        }
        setUnitCost(purchase.getUnitCost());
        setUpdatedOn(purchase.getUpdatedOn());
    }
}
