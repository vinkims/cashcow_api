package com.example.cashcow_api.dtos.user;

import javax.validation.constraints.Size;

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
    
    @Size(min = 4, max = 30)
    private String passcode;

    public UserProfileDTO(EUserProfile userProfile){
        if (userProfile == null){ return; }
    }
}
