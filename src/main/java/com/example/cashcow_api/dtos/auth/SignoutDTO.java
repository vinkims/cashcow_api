package com.example.cashcow_api.dtos.auth;

import lombok.Data;

@Data
public class SignoutDTO {
    
    private String token;

    private Integer userId;
}
