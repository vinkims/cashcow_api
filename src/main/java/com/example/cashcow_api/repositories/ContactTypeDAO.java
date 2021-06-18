package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EContactType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTypeDAO extends JpaRepository<EContactType, Integer>{
    
}
