package com.example.cashcow_api.services.audit_event;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.audit_event.AuditEventTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EAuditEventType;
import com.example.cashcow_api.repositories.AuditEventTypeDAO;

@Service
public class SAuditEventType implements IAuditEventType {

    @Autowired
    private AuditEventTypeDAO auditEventTypeDAO;

    @Override
    public EAuditEventType create(AuditEventTypeDTO auditEventTypeDTO) {
        
        EAuditEventType auditEventType = new EAuditEventType();
        auditEventType.setName(auditEventTypeDTO.getName());
        
        save(auditEventType);
        return auditEventType;
    }

    @Override
    public Optional<EAuditEventType> getById(Integer typeId) {
        return auditEventTypeDAO.findById(typeId);
    }

    @Override
    public EAuditEventType getById(Integer typeId, Boolean handleException) {
        Optional<EAuditEventType> auditEventType = getById(typeId);
        if (!auditEventType.isPresent() && handleException) {
            throw new NotFoundException("audit event type with specified id not found", "auditEventTypeid");
        }
        return auditEventType.get();
    }

    @Override
    public Optional<EAuditEventType> getByName(String name) {
        return auditEventTypeDAO.findByName(name);
    }

    @Override
    public void save(EAuditEventType auditEventType) {
        auditEventTypeDAO.save(auditEventType);
    }
    
}
