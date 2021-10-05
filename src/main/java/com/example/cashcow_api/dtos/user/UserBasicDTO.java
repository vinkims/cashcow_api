package com.example.cashcow_api.dtos.user;

import com.example.cashcow_api.models.EUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBasicDTO {
    
    private String firstName;

    private String lastName;

    private String role;

    private Integer userId;

    public UserBasicDTO(EUser user){
        setFirstName(user.getFirstName());
        if (user.getLastName() != null){
            setLastName(user.getLastName());
        }
        setRole(user.getRole().getName());
        setUserId(user.getId());
    }
}
