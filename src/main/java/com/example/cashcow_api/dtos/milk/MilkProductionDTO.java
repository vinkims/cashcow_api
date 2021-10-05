package com.example.cashcow_api.dtos.milk;

import java.math.BigDecimal;

import com.example.cashcow_api.dtos.cow.CowBasicDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.EMilkProduction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MilkProductionDTO {
    
    private CowBasicDTO cow;

    private Integer cowId;

    private Integer id;

    private String session;

    private Integer sessionId;

    private BigDecimal quantity;

    private UserBasicDTO user;

    private Integer userId;

    public MilkProductionDTO(EMilkProduction production){
        setCow(new CowBasicDTO(production.getCow()));
        setId(production.getId());
        setSession(production.getMilkingSession().getName());
        setQuantity(production.getQuantity());
        setUser(new UserBasicDTO(production.getUser()));
    }
}
