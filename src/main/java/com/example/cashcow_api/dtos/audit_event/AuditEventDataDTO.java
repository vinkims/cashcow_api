package com.example.cashcow_api.dtos.audit_event;

import com.example.cashcow_api.models.EAuditEventData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditEventDataDTO {
    
    private String remoteAddress;

    private String sessionId;

    private String dataType;

    private String eventMessage;

    public AuditEventDataDTO(EAuditEventData auditEventData) {
        setDataType(auditEventData.getDataType());
        setEventMessage(auditEventData.getEventMessage());
        setRemoteAddress(auditEventData.getRemoteAddress());
        setSessionId(auditEventData.getSessionId());
    }
}
