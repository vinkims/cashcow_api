package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECowProfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CowProfileDAO extends JpaRepository<ECowProfile, Integer>{
    
}
