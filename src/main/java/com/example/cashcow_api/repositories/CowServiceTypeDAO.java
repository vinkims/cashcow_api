package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECowServiceType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CowServiceTypeDAO extends JpaRepository<ECowServiceType, Integer> {
    
    Boolean existsByName(String name);
}
