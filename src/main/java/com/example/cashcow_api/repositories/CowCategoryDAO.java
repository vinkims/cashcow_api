package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECowCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CowCategoryDAO extends JpaRepository<ECowCategory, Integer>, JpaSpecificationExecutor<ECowCategory> {
    
}
