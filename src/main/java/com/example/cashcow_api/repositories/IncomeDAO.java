package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.cashcow_api.models.EIncome;

public interface IncomeDAO extends JpaRepository<EIncome, Integer>, JpaSpecificationExecutor<EIncome> {
    
}
