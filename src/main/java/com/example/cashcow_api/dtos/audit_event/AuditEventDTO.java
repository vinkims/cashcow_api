package com.example.cashcow_api.dtos.audit_event;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EAuditEvent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AuditEventDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private String principal;

    @JsonIgnoreProperties("id")
    private AuditEventTypeDTO eventType;

    private String eventTypeName;

    private AuditEventDataDTO eventData;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public AuditEventDTO(EAuditEvent auditEvent) {
        setCreatedOn(auditEvent.getCreatedOn());
        if (auditEvent.getEventData() != null) {
            setEventData(new AuditEventDataDTO(auditEvent.getEventData()));
        }
        if (auditEvent.getEventType() != null) {
            setEventType(new AuditEventTypeDTO(auditEvent.getEventType()));
        }
        if (auditEvent.getFarm() != null) {
            setFarm(new FarmDTO(auditEvent.getFarm()));
        }
        setId(auditEvent.getId());
        setPrincipal(auditEvent.getPrincipal());
        setStatus(new StatusDTO(auditEvent.getStatus()));
    }
}
