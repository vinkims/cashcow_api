package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkShopDelivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MilkShopDeliveryDAO extends JpaRepository<EMilkShopDelivery, Integer>, JpaSpecificationExecutor<EMilkShopDelivery> {
    
}