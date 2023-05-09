package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.cashcow_api.models.EBreed;

public interface BreedDAO extends JpaRepository<EBreed, Integer>, JpaSpecificationExecutor<EBreed> {
    
    Boolean existsByName(String name);
}
