package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECowService;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CowServiceDAO extends JpaRepository<ECowService, Integer>{
    
}
