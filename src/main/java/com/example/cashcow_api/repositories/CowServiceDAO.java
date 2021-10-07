package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECowService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CowServiceDAO extends JpaRepository<ECowService, Integer>, JpaSpecificationExecutor<ECowService>{
    
}
