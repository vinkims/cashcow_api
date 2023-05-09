package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ETransactionType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionTypeDAO extends JpaRepository<ETransactionType, Integer>, JpaSpecificationExecutor<ETransactionType> {
    
}
