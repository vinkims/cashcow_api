package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.ECowFeeding;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowFeedingDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private FarmDTO farm;

    private Integer farmId;

    private UserBasicDTO user;

    private Integer userId;

    private StatusDTO status;

    private Integer statusId;

    public CowFeedingDTO(ECowFeeding cowFeeding) {
        setCreatedOn(cowFeeding.getCreatedOn());
        setFarm(new FarmDTO(cowFeeding.getFarm()));
        setId(cowFeeding.getId());
        setStatus(new StatusDTO(cowFeeding.getStatus()));
        setUpdatedOn(cowFeeding.getUpdatedOn());
        setUser(new UserBasicDTO(cowFeeding.getUser()));
    }
}
