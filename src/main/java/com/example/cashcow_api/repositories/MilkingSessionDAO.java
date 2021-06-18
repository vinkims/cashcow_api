package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkingSession;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MilkingSessionDAO extends JpaRepository<EMilkingSession, Integer>{
    
}
