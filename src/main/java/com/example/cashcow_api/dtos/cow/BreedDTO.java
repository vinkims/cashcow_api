package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import com.example.cashcow_api.annotations.IsBreedNameValid;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EBreed;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class BreedDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    @IsBreedNameValid
    private String name;

    private String description;

    private StatusDTO status;

    private Integer statusId;

    public BreedDTO(EBreed breed) {
        setCreatedOn(breed.getCreatedOn());
        setDescription(breed.getDescription());
        setId(breed.getId());
        setName(breed.getName());
        setStatus(new StatusDTO(breed.getStatus()));
    }
}
