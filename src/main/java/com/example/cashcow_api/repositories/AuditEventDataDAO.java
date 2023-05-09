package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cashcow_api.models.EAuditEvent;
import com.example.cashcow_api.models.EAuditEventData;

public interface AuditEventDataDAO extends JpaRepository<EAuditEventData, EAuditEvent> {
    
}
