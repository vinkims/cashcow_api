package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkSale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MilkSaleDAO extends JpaRepository<EMilkSale, Integer>{
    
}
