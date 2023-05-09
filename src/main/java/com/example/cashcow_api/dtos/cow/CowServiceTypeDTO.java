package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsCowServiceTypeNameValid;
import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.ECowServiceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class CowServiceTypeDTO {

    private Integer id;

    private LocalDateTime createdOn;

    @NotBlank
    @IsCowServiceTypeNameValid
    private String name;

    private String description;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public CowServiceTypeDTO(ECowServiceType cowServiceType){
        setCreatedOn(cowServiceType.getCreatedOn());
        setDescription(cowServiceType.getDescription());
        setFarm(new FarmDTO(cowServiceType.getFarm()));
        setId(cowServiceType.getId());
        setName(cowServiceType.getName());
        setStatus(new StatusDTO(cowServiceType.getStatus()));
    }
}
