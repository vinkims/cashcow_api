package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECowCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CowCategoryDAO extends JpaRepository<ECowCategory, Integer>{
    
}
