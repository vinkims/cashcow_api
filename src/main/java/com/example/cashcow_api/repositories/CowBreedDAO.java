package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.cashcow_api.models.ECowBreed;

public interface CowBreedDAO extends JpaRepository<ECowBreed, Integer>, JpaSpecificationExecutor<ECowBreed> {
    
    Boolean existsByName(String name);
}
