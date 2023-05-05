package com.example.cashcow_api.services.audit_event;

import java.util.Optional;

import com.example.cashcow_api.dtos.audit_event.AuditEventTypeDTO;
import com.example.cashcow_api.models.EAuditEventType;

public interface IAuditEventType {
    
    EAuditEventType create(AuditEventTypeDTO auditEventTypeDTO);

    Optional<EAuditEventType> getById(Integer typeId);

    EAuditEventType getById(Integer typeId, Boolean handleException);

    Optional<EAuditEventType> getByName(String name);

    void save(EAuditEventType auditEventType);
}
