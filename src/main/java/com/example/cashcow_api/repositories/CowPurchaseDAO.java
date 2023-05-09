package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.cashcow_api.models.ECowPurchase;

public interface CowPurchaseDAO extends JpaRepository<ECowPurchase, Integer>, JpaSpecificationExecutor<ECowPurchase> {
    
}
