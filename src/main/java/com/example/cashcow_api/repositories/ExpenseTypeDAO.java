package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EExpenseType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseTypeDAO extends JpaRepository<EExpenseType, Integer>, JpaSpecificationExecutor<EExpenseType> {
    
    Boolean existsByName(String name);
}
