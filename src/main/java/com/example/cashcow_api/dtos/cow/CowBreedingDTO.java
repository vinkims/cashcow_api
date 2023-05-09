package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.breeding.BreedingTypeDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.ECowBreeding;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowBreedingDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @JsonIgnoreProperties("description")
    private BreedingTypeDTO breedingType;

    private Integer breedingTypeId;

    private CowBasicDTO cow;

    private Integer cowId;

    private CowBasicDTO bull;

    private Integer bullId;

    private String description;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public CowBreedingDTO(ECowBreeding cowBreeding) {
        if (cowBreeding.getBreedingType() != null) {
            setBreedingType(new BreedingTypeDTO(cowBreeding.getBreedingType()));
        }
        if (cowBreeding.getBull() != null) {
            setBull(new CowBasicDTO(cowBreeding.getBull()));
        }
        if (cowBreeding.getCow() != null) {
            setCow(new CowBasicDTO(cowBreeding.getCow()));
        }
        setCreatedOn(cowBreeding.getCreatedOn());
        setDescription(cowBreeding.getDescription());
        setId(cowBreeding.getId());
        setStatus(new StatusDTO(cowBreeding.getStatus()));
        setUpdatedOn(cowBreeding.getUpdatedOn());
    }
}
