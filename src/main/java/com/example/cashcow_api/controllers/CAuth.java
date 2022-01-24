package com.example.cashcow_api.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.cashcow_api.dtos.auth.AuthDTO;
import com.example.cashcow_api.dtos.auth.SignoutDTO;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.auth.IAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CAuth {
    
    @Autowired
    private IAuth sAuth;

    @PostMapping(path = "/user/auth", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> authenticateUser(@RequestBody AuthDTO authDTO){

        String token = sAuth.authenticateUser(authDTO);

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully authenticated user", res));
    }

    @PostMapping(path = "/user/auth/sign-out", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> signoutUser(@RequestBody SignoutDTO signoutDTO){

        Boolean isSignout = sAuth.signoutUser(signoutDTO);

        Map<String, Object> content = new HashMap<>();
        content.put("signout", isSignout);
        content.put("userId", signoutDTO.getUserId());
        content.put("timestamp", new Date().toString());

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "signout successful", content));
    }
}
