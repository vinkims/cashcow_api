package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CowDAO extends JpaRepository<ECow, Integer>, JpaSpecificationExecutor<ECow> {
    
    Boolean existsByName(String cowName);
}
