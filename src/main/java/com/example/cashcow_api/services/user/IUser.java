package com.example.cashcow_api.services.user;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.user.UserDTO;
import com.example.cashcow_api.models.EUser;

public interface IUser {
    
    EUser create(UserDTO userDTO);

    Optional<EUser> getByContactValue(String contactValue);

    Optional<EUser> getById(Integer userId);

    List<EUser> getAllUsers();

    void save(EUser user);
}
