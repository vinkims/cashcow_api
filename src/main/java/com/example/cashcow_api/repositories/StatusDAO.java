package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusDAO extends JpaRepository<EStatus, Integer>{
    
}
