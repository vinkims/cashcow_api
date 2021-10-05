package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkProduction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MilkProductionDAO extends JpaRepository<EMilkProduction, Integer>, JpaSpecificationExecutor<EMilkProduction>{
    
}
