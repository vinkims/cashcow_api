package com.example.cashcow_api.dtos.cow;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.ECowSale;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowSaleDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private CowBasicDTO cow;

    private Integer cowId;

    private BigDecimal saleAmount;

    private UserBasicDTO buyer;

    private Integer buyerId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public CowSaleDTO(ECowSale cowSale) {
        if (cowSale.getBuyer() != null) {
            setBuyer(new UserBasicDTO(cowSale.getBuyer()));
        }
        setCow(new CowBasicDTO(cowSale.getCow()));
        setCreatedOn(cowSale.getCreatedOn());
        setId(cowSale.getId());
        setSaleAmount(cowSale.getSaleAmount());
        setStatus(new StatusDTO(cowSale.getStatus()));
        setUpdatedOn(cowSale.getUpdatedOn());
    }
}
