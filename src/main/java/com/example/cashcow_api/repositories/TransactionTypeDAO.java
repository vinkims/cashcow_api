package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ETransactionType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeDAO extends JpaRepository<ETransactionType, Integer>{
    
}
