package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EMilkSale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MilkSaleDAO extends JpaRepository<EMilkSale, Integer>, JpaSpecificationExecutor<EMilkSale>{
    
}
