package com.example.cashcow_api.dtos.role;

import com.example.cashcow_api.models.ERole;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class RoleDTO {
    
    private Integer id;

    private String name;

    private String description;

    public RoleDTO(ERole role) {
        setDescription(role.getDescription());
        setId(role.getId());
        setName(role.getName());
    }
}
