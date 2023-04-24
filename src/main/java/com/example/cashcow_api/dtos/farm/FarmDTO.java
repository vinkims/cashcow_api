package com.example.cashcow_api.dtos.farm;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsFarmNameValid;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EFarm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class FarmDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @NotBlank
    @IsFarmNameValid
    private String name;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public FarmDTO(EFarm farm){
        setCreatedOn(farm.getCreatedOn());
        setId(farm.getId());
        setName(farm.getName());
        setStatus(new StatusDTO(farm.getStatus()));
        setUpdatedOn(farm.getUpdatedOn());
    }
}
