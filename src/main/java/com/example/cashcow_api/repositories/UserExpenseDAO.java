package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cashcow_api.models.EUserExpense;
import com.example.cashcow_api.models.composites.UserExpensePK;

public interface UserExpenseDAO extends JpaRepository<EUserExpense, UserExpensePK> {
    
}
