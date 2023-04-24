package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EFarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FarmDAO extends JpaRepository<EFarm, Integer>, JpaSpecificationExecutor<EFarm> {

    Boolean existsByName(String farmName);
}
