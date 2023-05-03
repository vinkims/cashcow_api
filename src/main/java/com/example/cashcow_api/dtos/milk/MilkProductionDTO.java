package com.example.cashcow_api.dtos.milk;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.cow.CowBasicDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
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
    
    private Integer id;

    private CowBasicDTO cow;

    private Integer cowId;

    private LocalDateTime productionDate;

    private LocalDateTime updatedOn;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "description", "farm", "status"})
    private MilkingSessionDTO milkingSession;

    private Integer sessionId;

    private Float quantity;

    private UserBasicDTO user;

    private Integer userId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public MilkProductionDTO(EMilkProduction production){
        setCow(new CowBasicDTO(production.getCow()));
        setId(production.getId());
        setMilkingSession(new MilkingSessionDTO(production.getMilkingSession()));
        setProductionDate(production.getCreatedOn());
        setQuantity(production.getQuantity());
        if (production.getStatus() != null) {
            setStatus(new StatusDTO(production.getStatus()));
        }
        setUpdatedOn(production.getUpdatedOn());
        setUser(new UserBasicDTO(production.getUser()));
    }
}
