package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkingSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MilkingSessionDAO extends JpaRepository<EMilkingSession, Integer>, JpaSpecificationExecutor<EMilkingSession> {
    
}
