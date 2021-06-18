package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkProduction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MilkProductionDAO extends JpaRepository<EMilkProduction, Integer>{
    
}
