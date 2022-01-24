package com.example.cashcow_api.services.auth;

import com.example.cashcow_api.dtos.auth.AuthDTO;
import com.example.cashcow_api.dtos.auth.SignoutDTO;
import com.example.cashcow_api.models.EUser;

public interface IAuth {
    
    String authenticateUser(AuthDTO authDTO);

    EUser getUser(Integer userId);

    public Boolean signoutUser(SignoutDTO signoutDTO);
}
