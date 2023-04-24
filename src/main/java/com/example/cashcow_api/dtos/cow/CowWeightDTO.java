package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.ECowWeight;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowWeightDTO {
    
    private Integer id;

    private CowBasicDTO cow;

    private Integer cowId;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private Float weight;

    private StatusDTO status;

    private Integer statusId;

    public CowWeightDTO(ECowWeight cowWeight) {
        setCow(new CowBasicDTO(cowWeight.getCow()));
        setCreatedOn(cowWeight.getCreatedOn());
        setId(cowWeight.getId());
        if (cowWeight.getStatus() != null) {
            setStatus(new StatusDTO(cowWeight.getStatus()));
        }
        setUpdatedOn(cowWeight.getUpdatedOn());
        setWeight(cowWeight.getWeight());
    }
}
