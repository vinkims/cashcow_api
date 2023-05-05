package com.example.cashcow_api.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.example.cashcow_api.dtos.audit_event.AuditEventDTO;
import com.example.cashcow_api.dtos.audit_event.AuditEventDataDTO;
import com.example.cashcow_api.dtos.audit_event.AuditEventTypeDTO;
import com.example.cashcow_api.services.audit_event.IAuditEvent;

@Component
public class AuditApplicationEventsListener {

    Logger logger = LoggerFactory.getLogger(AuditApplicationEventsListener.class);
    
    @Autowired
    private IAuditEvent sAuditEvent;

    @EventListener
    public void onAuditEvent(AuditApplicationEvent event) {

        AuditEvent auditEvent = event.getAuditEvent();
        
        logger.info("\nOn audit application event:\ntimestamp: {}\nprincipal: {}\ntype: {}\ndata: {}",
            auditEvent.getTimestamp(),
            auditEvent.getPrincipal(),
            auditEvent.getType(),
            auditEvent.getData()
        );

        try {
            AuditEventDTO auditEventDTO = new AuditEventDTO();
            auditEventDTO.setEventTypeName(auditEvent.getType());
            auditEventDTO.setPrincipal(auditEvent.getPrincipal());

            if (auditEvent.getData() != null) {
                AuditEventDataDTO eventDataDTO = new AuditEventDataDTO();
                if (auditEvent.getData().get("type") != null) {
                    eventDataDTO.setDataType((String) auditEvent.getData().get("type"));
                }
                if (auditEvent.getData().get("message") != null) {
                    eventDataDTO.setEventMessage((String) auditEvent.getData().get("message"));
                }
                if (auditEvent.getData().get("details") != null) {
                    WebAuthenticationDetails authDetails = (WebAuthenticationDetails) auditEvent.getData().get("details");
                    eventDataDTO.setRemoteAddress(authDetails.getRemoteAddress());
                    eventDataDTO.setSessionId(authDetails.getSessionId());
                }
                auditEventDTO.setEventData(eventDataDTO);
            }

            AuditEventTypeDTO eventTypeDTO = new AuditEventTypeDTO();
            eventTypeDTO.setName(auditEvent.getType());
            auditEventDTO.setEventType(eventTypeDTO);

            sAuditEvent.create(auditEventDTO);
        } catch (Exception e) {
            logger.error("\n[LOCATION] - AuditApplicationEventsListener.onAuditEvent\n[CAUSE] - {}\n[MSG] - {}",
                e.getCause(), e.getMessage()
            );
        }
    }
}
