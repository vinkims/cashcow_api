package com.example.cashcow_api.services.user;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.user.UserDTO;
import com.example.cashcow_api.models.EUser;

import org.springframework.data.domain.Page;

public interface IUser {
    
    EUser create(UserDTO userDTO);

    Optional<EUser> getByContactValue(String contactValue);

    Optional<EUser> getById(Integer userId);

    Optional<EUser> getByIdOrContact(String userValue);

    Page<EUser> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EUser user);

    EUser update(EUser user, UserDTO userDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
