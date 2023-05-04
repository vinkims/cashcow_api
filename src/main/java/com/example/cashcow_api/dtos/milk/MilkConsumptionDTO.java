package com.example.cashcow_api.dtos.milk;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
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
    
    private Integer id;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "farm", "description", "status"})
    private MilkConsumptionCategoryDTO category;

    private Integer categoryId;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private BigDecimal unitCost;

    private Float quantity;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "description", "farm", "status"})
    private MilkingSessionDTO session;

    private Integer sessionId;

    private UserBasicDTO user;

    private Integer userId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public MilkConsumptionDTO(EMilkConsumption consumption){
        setCategory(new MilkConsumptionCategoryDTO(consumption.getCategory()));
        setCreatedOn(consumption.getCreatedOn());
        if (consumption.getFarm() != null) {
            setFarm(new FarmDTO(consumption.getFarm()));
        }
        setId(consumption.getId());
        setQuantity(consumption.getQuantity());
        if (consumption.getSession() != null) {
            setSession(new MilkingSessionDTO(consumption.getSession()));
        }
        if (consumption.getStatus() != null) {
            setStatus(new StatusDTO(consumption.getStatus()));
        }
        setUnitCost(consumption.getUnitCost());
        setUpdatedOn(consumption.getUpdatedOn());
        setUser(new UserBasicDTO(consumption.getUser()));
    }
}
