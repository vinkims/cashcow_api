package com.example.cashcow_api.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthDTO {
    
    private String userContact;

    @JsonProperty(value = "password")
    private String passcode;
}
