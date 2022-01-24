package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EShop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopDAO extends JpaRepository<EShop, Integer>{
    
    Boolean existsByName(String shopName);
}
