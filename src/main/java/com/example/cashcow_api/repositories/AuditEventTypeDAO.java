package com.example.cashcow_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cashcow_api.models.EAuditEventType;

public interface AuditEventTypeDAO extends JpaRepository<EAuditEventType, Integer> {
    
    Optional<EAuditEventType> findByName(String name);
}
