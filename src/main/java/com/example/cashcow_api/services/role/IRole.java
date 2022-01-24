package com.example.cashcow_api.services.role;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.ERole;

public interface IRole {
    
    List<ERole> getAll();
    
    Optional<ERole> getById(Integer roleId);
}
