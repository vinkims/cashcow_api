package com.example.cashcow_api.dtos.milk;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EMilkConsumptionCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class MilkConsumptionCategoryDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String name;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private String description;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public MilkConsumptionCategoryDTO(EMilkConsumptionCategory category){
        setCreatedOn(category.getCreatedOn());
        setDescription(category.getDescription());
        setFarm(new FarmDTO(category.getFarm()));
        setId(category.getId());
        setName(category.getName());
        setStatus(new StatusDTO(category.getStatus()));
        setUpdatedOn(category.getUpdatedOn());
    }
}
