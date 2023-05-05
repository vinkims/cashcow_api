package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cashcow_api.models.ECowExpense;
import com.example.cashcow_api.models.composites.CowExpensePK;

public interface CowExpenseDAO extends JpaRepository<ECowExpense, CowExpensePK> {
    
}
