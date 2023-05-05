package com.example.cashcow_api.services.audit_event;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.audit_event.AuditEventDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EAuditEvent;

public interface IAuditEvent {
    
    EAuditEvent create(AuditEventDTO auditEventDTO);

    Optional<EAuditEvent> getById(Integer eventId);

    EAuditEvent getById(Integer eventId, Boolean handleException);

    Page<EAuditEvent> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EAuditEvent auditEvent);

    EAuditEvent update(Integer eventId, AuditEventDTO auditEventDTO);
}
