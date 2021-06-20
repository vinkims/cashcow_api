package com.example.cashcow_api.services;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.ERole;
import com.example.cashcow_api.repositories.RoleDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SRole {
    
    @Autowired private RoleDAO roleDAO;

    public Optional<ERole> getById(Integer roleId){
        return roleDAO.findById(roleId);
    }

    public List<ERole> getAll(){
        return roleDAO.findAll();
    }
}
