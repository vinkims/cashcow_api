package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EExpenseType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeDAO extends JpaRepository<EExpenseType, Integer> {
    
    Boolean existsByName(String name);
}
