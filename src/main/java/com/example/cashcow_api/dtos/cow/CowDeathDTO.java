package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.ECowDeath;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowDeathDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private CowBasicDTO cow;

    private Integer cowId;

    private String description;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public CowDeathDTO(ECowDeath cowDeath) {
        setCow(new CowBasicDTO(cowDeath.getCow()));
        setCreatedOn(cowDeath.getCreatedOn());
        setDescription(cowDeath.getDescription());
        setId(cowDeath.getId());
        setStatus(new StatusDTO(cowDeath.getStatus()));
        setUpdatedOn(cowDeath.getUpdatedOn());
    }
}
