package com.example.cashcow_api.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "audit_event_data")
@Data
@NoArgsConstructor
public class EAuditEventData implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "audit_event_id", referencedColumnName = "id")
    private EAuditEvent auditEvent;

    @Id
    @Column(name = "audit_event_id")
    private Integer auditEventId;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "event_message")
    private String eventMessage;

    @Column(name = "remote_address")
    private String remoteAddress;

    @Column(name = "session_id")
    private String sessionId;

    public void setAuditEvent(EAuditEvent auditEvent) {
        this.auditEvent = auditEvent;
        this.auditEventId = auditEvent.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        EAuditEventData auditEventData = (EAuditEventData) o;
        return auditEvent.getId() == auditEventData.getAuditEvent().getId();
    }

    @Override
    public int hashCode() {
        int hash = 65;
        hash = 31 * hash + auditEvent.getId().intValue();
        return hash;
    }
}
