package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EShop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopDAO extends JpaRepository<EShop, Integer>, JpaSpecificationExecutor<EShop> {
    
    Boolean existsByName(String shopName);
}
