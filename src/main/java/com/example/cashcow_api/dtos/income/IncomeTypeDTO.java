package com.example.cashcow_api.dtos.income;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EIncomeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class IncomeTypeDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private String name;

    private String description;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public IncomeTypeDTO(EIncomeType incomeType) {
        setCreatedOn(incomeType.getCreatedOn());
        setDescription(incomeType.getDescription());
        if (incomeType.getFarm() != null) {
            setFarm(new FarmDTO(incomeType.getFarm()));
        }
        setId(incomeType.getId());
        setName(incomeType.getName());
        setStatus(new StatusDTO(incomeType.getStatus()));
        setUpdatedOn(incomeType.getUpdatedOn());
    }
}
