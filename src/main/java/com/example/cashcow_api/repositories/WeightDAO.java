package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EWeight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WeightDAO extends JpaRepository<EWeight, Integer>, JpaSpecificationExecutor<EWeight> {
    
}
