package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECowServiceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CowServiceTypeDAO extends JpaRepository<ECowServiceType, Integer>, JpaSpecificationExecutor<ECowServiceType> {
    
    Boolean existsByName(String name);
}
