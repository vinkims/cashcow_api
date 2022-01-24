package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EFarm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmDAO extends JpaRepository<EFarm, Integer> {

    Boolean existsByName(String farmName);
}
