package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkDelivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MilkDeliveryDAO extends JpaRepository<EMilkDelivery, Integer>, JpaSpecificationExecutor<EMilkDelivery> {
    
}
