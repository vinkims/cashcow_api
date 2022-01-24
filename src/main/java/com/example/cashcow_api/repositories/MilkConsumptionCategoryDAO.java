package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkConsumptionCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MilkConsumptionCategoryDAO extends JpaRepository<EMilkConsumptionCategory, Integer>{
    
}
