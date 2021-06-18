package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ERole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<ERole, Integer>{
    
}
