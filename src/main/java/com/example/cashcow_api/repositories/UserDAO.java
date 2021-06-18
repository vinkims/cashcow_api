package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<EUser, Integer>{
    
}
