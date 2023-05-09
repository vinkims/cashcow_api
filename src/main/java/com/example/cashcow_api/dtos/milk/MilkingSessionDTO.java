package com.example.cashcow_api.dtos.milk;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EMilkingSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class MilkingSessionDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String name;

    private String description;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public MilkingSessionDTO(EMilkingSession milkingSession) {
        setCreatedOn(milkingSession.getCreatedOn());
        setDescription(milkingSession.getDescription());
        setFarm(new FarmDTO(milkingSession.getFarm()));
        setId(milkingSession.getId());
        setName(milkingSession.getName());
        setStatus(new StatusDTO(milkingSession.getStatus()));
        setUpdatedOn(milkingSession.getUpdatedOn());
    }
}
