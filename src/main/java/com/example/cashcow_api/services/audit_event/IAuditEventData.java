package com.example.cashcow_api.services.audit_event;

import com.example.cashcow_api.dtos.audit_event.AuditEventDataDTO;
import com.example.cashcow_api.models.EAuditEvent;
import com.example.cashcow_api.models.EAuditEventData;

public interface IAuditEventData {
    
    EAuditEventData create(AuditEventDataDTO auditEventDataDTO, EAuditEvent auditEvent);

    void save(EAuditEventData auditEventData);
}
