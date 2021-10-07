package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ETransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionDAO extends JpaRepository<ETransaction, Integer>, JpaSpecificationExecutor<ETransaction> {
    
}
