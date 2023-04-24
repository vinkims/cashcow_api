package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.ECowBreed;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowBreedDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private String name;

    private String description;

    private StatusDTO status;

    private Integer statusId;

    public CowBreedDTO(ECowBreed breed) {
        setCreatedOn(breed.getCreatedOn());
        setDescription(breed.getDescription());
        setId(breed.getId());
        setName(breed.getName());
        setStatus(new StatusDTO(breed.getStatus()));
    }
}
