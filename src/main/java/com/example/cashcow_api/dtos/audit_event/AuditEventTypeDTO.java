package com.example.cashcow_api.dtos.audit_event;

import com.example.cashcow_api.models.EAuditEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AuditEventTypeDTO {
    
    private Integer id;

    private String name;

    public AuditEventTypeDTO(EAuditEventType auditEventType) {
        setId(auditEventType.getId());
        setName(auditEventType.getName());
    }
}
