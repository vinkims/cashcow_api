package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ESaleType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleTypeDAO extends JpaRepository<ESaleType, Integer> {
    
}
