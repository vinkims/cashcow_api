package com.example.cashcow_api.services.audit_event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.audit_event.AuditEventDataDTO;
import com.example.cashcow_api.models.EAuditEvent;
import com.example.cashcow_api.models.EAuditEventData;
import com.example.cashcow_api.repositories.AuditEventDataDAO;

@Service
public class SAuditEventData implements IAuditEventData {

    @Autowired
    private AuditEventDataDAO auditEventDataDAO;

    @Override
    public EAuditEventData create(AuditEventDataDTO auditEventDataDTO, EAuditEvent auditEvent) {

        EAuditEventData eventData = auditEvent.getEventData() != null ? auditEvent.getEventData() : new EAuditEventData();
        eventData.setAuditEvent(auditEvent);
        eventData.setDataType(auditEventDataDTO.getDataType());
        eventData.setEventMessage(auditEventDataDTO.getEventMessage());
        eventData.setRemoteAddress(auditEventDataDTO.getRemoteAddress());
        eventData.setSessionId(auditEventDataDTO.getSessionId());

        save(eventData);
        return eventData;
    }

    @Override
    public void save(EAuditEventData auditEventData) {
        auditEventDataDAO.save(auditEventData);
    }
    
}
