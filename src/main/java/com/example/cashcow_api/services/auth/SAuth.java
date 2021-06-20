package com.example.cashcow_api.services.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.auth.AuthDTO;
import com.example.cashcow_api.dtos.auth.SignoutDTO;
import com.example.cashcow_api.exceptions.InvalidInputException;
import com.example.cashcow_api.models.EBlacklistToken;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.services.SUser;
import com.example.cashcow_api.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SAuth {
    
    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JwtUtil jwtUtil;

    @Autowired private SBlacklist sBlacklist;

    @Autowired private SUser sUser;

    @Autowired private SUserDetails sUserDetails;

    public String authenticateUser(AuthDTO authDTO){
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authDTO.getUserContact(), authDTO.getPasscode()
                )
            );
        }catch(BadCredentialsException ex){
            log.info("{}", ex.getLocalizedMessage());
            throw new InvalidInputException("invalid credentials provided", "user/password");
        }

        UserDetails userDetails = sUserDetails.loadUserByUsername(authDTO.getUserContact());

        EUser user = sUser.getByContactValue(authDTO.getUserContact()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        final String token = jwtUtil.generateToken(userDetails, claims);

        return token;

    }

    public EUser getUser(Integer userId){
        Optional<EUser> user = sUser.getById(userId);
        if (!user.isPresent()){
            throw new InvalidInputException("user not found", "userId");
        }
        return user.get();
    }

    public Boolean signoutUser(SignoutDTO signoutDTO){

        EUser user = getUser(signoutDTO.getUserId());
        EBlacklistToken blacklistToken = sBlacklist.createBlacklistToken(signoutDTO.getToken(), user);

        return blacklistToken != null;
    }
}