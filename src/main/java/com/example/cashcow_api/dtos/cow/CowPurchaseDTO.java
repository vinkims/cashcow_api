package com.example.cashcow_api.dtos.cow;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.ECowPurchase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowPurchaseDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private CowBasicDTO cow;

    private Integer cowId;

    private BigDecimal purchaseAmount;

    private String purchaseLocation;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public CowPurchaseDTO(ECowPurchase cowPurchase) {
        setCow(new CowBasicDTO(cowPurchase.getCow()));
        setCreatedOn(cowPurchase.getCreatedOn());
        setId(cowPurchase.getId());
        setPurchaseAmount(cowPurchase.getPurchaseAmount());
        setPurchaseLocation(cowPurchase.getPurchaseLocation());
        setStatus(new StatusDTO(cowPurchase.getStatus()))
        setUpdatedOn(cowPurchase.getUpdatedOn());
    }
}
