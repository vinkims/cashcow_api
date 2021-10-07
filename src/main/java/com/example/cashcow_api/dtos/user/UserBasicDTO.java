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
    
    private String name;

    private String role;

    private Integer userId;

    public UserBasicDTO(EUser user){
        String firstName = user.getFirstName();
        String lastName = user.getLastName() != null || !user.getLastName().isEmpty() ?
            user.getLastName() : "";
        setName(firstName + " " + lastName);
        setUserId(user.getId());
    }
}
