package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EExpense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseDAO extends JpaRepository<EExpense, Integer>, JpaSpecificationExecutor<EExpense> {
    
}
