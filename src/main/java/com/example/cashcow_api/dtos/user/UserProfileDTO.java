package com.example.cashcow_api.dtos.user;

import com.example.cashcow_api.models.EUserProfile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDTO {
    
    private String passcode;

    public UserProfileDTO(EUserProfile userProfile){
        if (userProfile == null){ return; }
    }
}
