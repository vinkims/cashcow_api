package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ETransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDAO extends JpaRepository<ETransaction, Integer> {
    
}
