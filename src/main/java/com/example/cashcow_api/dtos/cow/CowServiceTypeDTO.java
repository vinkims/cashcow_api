package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsCowServiceTypeNameValid;
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
    
    private LocalDateTime createdOn;

    private String description;

    private Integer id;

    @NotBlank
    @IsCowServiceTypeNameValid
    private String name;

    public CowServiceTypeDTO(ECowServiceType cowServiceType){

        setCreatedOn(cowServiceType.getCreatedOn());
        setDescription(cowServiceType.getDescription());
        setId(cowServiceType.getId());
        setName(cowServiceType.getName());
    }
}
