package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EUserMilkPrice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMilkPriceDAO extends JpaRepository<EUserMilkPrice, Integer> {
    
}
