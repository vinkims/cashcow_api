package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkConsumptionCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MilkConsumptionCategoryDAO extends JpaRepository<EMilkConsumptionCategory, Integer>, JpaSpecificationExecutor<EMilkConsumptionCategory> {
    
}
