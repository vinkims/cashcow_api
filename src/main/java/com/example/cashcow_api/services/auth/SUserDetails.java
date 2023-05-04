package com.example.cashcow_api.services.auth;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.cashcow_api.exceptions.InvalidInputException;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.services.user.SUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SUserDetails implements UserDetailsService {

    @Autowired private SUser sUser;

    @Override
    public UserDetails loadUserByUsername(String contactValue) throws UsernameNotFoundException {
        
        Optional<EUser> user = sUser.getByContactValue(contactValue);
        if (!user.isPresent()){
            throw new InvalidInputException("invalid credentials provided", "user/password");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // converts to org.springframework.security.core.userdetails.UserDetails object
        String passcode = user.get().getPasscode();
        UserDetails userDetails = (UserDetails) new User(contactValue, passcode == null ? "" : passcode, grantedAuthorities);
        
        return userDetails;
    }

    /**
     * Gets active request's user
     */
    public EUser getActiveUserByContact(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<EUser> activeUser = sUser.getByContactValue(((UserDetails) principal).getUsername());

        return activeUser.get();
    }
    
}
