package com.example.cashcow_api.dtos.status;

import com.example.cashcow_api.models.EStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class StatusDTO {
    
    private Integer id;

    private String name;

    private String description;

    public StatusDTO(EStatus status) {
        setDescription(status.getName());
        setId(status.getId());
        setName(status.getName());
    }
}
