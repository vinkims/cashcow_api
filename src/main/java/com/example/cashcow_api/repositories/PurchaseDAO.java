package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EPurchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PurchaseDAO extends JpaRepository<EPurchase, Integer>, JpaSpecificationExecutor<EPurchase>{
    
}
