package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.cashcow_api.models.ECowDeath;

public interface CowDeathDAO extends JpaRepository<ECowDeath, Integer>, JpaSpecificationExecutor<ECowDeath> {
    
}
