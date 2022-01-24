package com.example.cashcow_api.dtos.milk;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.EMilkConsumption;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MilkConsumptionDTO {
    
    private MilkConsumptionCategoryDTO category;

    private Integer categoryId;

    private LocalDateTime createdOn;

    private Integer id;

    private Float litrePrice;

    private Float quantity;

    private String session;

    private Integer sessionId;

    private UserBasicDTO user;

    private Integer userId;

    public MilkConsumptionDTO(EMilkConsumption consumption){
        setCategory(new MilkConsumptionCategoryDTO(consumption.getCategory()));
        setCreatedOn(consumption.getCreatedOn());
        setId(consumption.getId());
        setLitrePrice(consumption.getLitrePrice());
        setQuantity(consumption.getQuantity());
        setSession(consumption.getSession().getName());
        setUser(new UserBasicDTO(consumption.getUser()));
    }
}
