package com.example.cashcow_api.services.audit_event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.audit_event.AuditEventDTO;
import com.example.cashcow_api.dtos.audit_event.AuditEventDataDTO;
import com.example.cashcow_api.dtos.audit_event.AuditEventTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EAuditEvent;
import com.example.cashcow_api.models.EAuditEventData;
import com.example.cashcow_api.models.EAuditEventType;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.AuditEventDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SAuditEvent implements IAuditEvent {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;
    
    @Autowired
    private AuditEventDAO auditEventDAO;

    @Autowired
    private IAuditEventData sAuditEventData;

    @Autowired
    private IAuditEventType sAuditEventType;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EAuditEvent create(AuditEventDTO auditEventDTO) {

        EAuditEvent auditEvent = new EAuditEvent();
        auditEvent.setCreatedOn(LocalDateTime.now());
        auditEvent.setPrincipal(auditEventDTO.getPrincipal());
        setEventType(auditEvent, auditEventDTO);
        setFarm(auditEvent, auditEventDTO.getFarmId());
        Integer statusId = auditEventDTO.getStatusId() == null ? completeStatusId : auditEventDTO.getStatusId();
        setStatus(auditEvent, statusId);

        save(auditEvent);
        setEventData(auditEvent, auditEventDTO.getEventData());
        return auditEvent;
    }

    @Override
    public Optional<EAuditEvent> getById(Integer eventId) {
        return auditEventDAO.findById(eventId);
    }

    @Override
    public EAuditEvent getById(Integer eventId, Boolean handleException) {
        Optional<EAuditEvent> auditEvent = getById(eventId);
        if (!auditEvent.isPresent() && handleException) {
            throw new NotFoundException("audit event with specified id not found", "auditEventId");
        }
        return auditEvent.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EAuditEvent> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EAuditEvent> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EAuditEvent>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "auditEvent");

        Specification<EAuditEvent> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return auditEventDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EAuditEvent auditEvent) {
        auditEventDAO.save(auditEvent);
    }

    public void setEventData(EAuditEvent auditEvent, AuditEventDataDTO eventDataDTO) {
        if (eventDataDTO == null) { return; }

        EAuditEventData auditEventData = sAuditEventData.create(eventDataDTO, auditEvent);
        auditEvent.setEventData(auditEventData);
    }

    public void setEventType(EAuditEvent auditEvent, AuditEventDTO auditEventDTO) {
        if (auditEventDTO.getEventTypeName() == null) { return; }

        String eventTypeName = auditEventDTO.getEventTypeName();
        Optional<EAuditEventType> eventTypeOpt = sAuditEventType.getByName(eventTypeName);
        EAuditEventType auditEventType;
        if (eventTypeOpt.isPresent()) {
            auditEventType = eventTypeOpt.get();
        } else {
            // Create new event type
            AuditEventTypeDTO eventTypeDTO = new AuditEventTypeDTO();
            eventTypeDTO.setName(eventTypeName);
            auditEventType = sAuditEventType.create(eventTypeDTO);
        }
        auditEvent.setEventType(auditEventType);
    }

    public void setFarm(EAuditEvent auditEvent, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        auditEvent.setFarm(farm);
    }

    public void setStatus(EAuditEvent auditEvent, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        auditEvent.setStatus(status);
    }

    @Override
    public EAuditEvent update(Integer eventId, AuditEventDTO auditEventDTO) {

        EAuditEvent auditEvent = getById(eventId, true);
        if (auditEventDTO.getPrincipal() != null) {
            auditEvent.setPrincipal(auditEventDTO.getPrincipal());
        }
        setEventType(auditEvent, auditEventDTO);
        setFarm(auditEvent, auditEventDTO.getFarmId());
        setStatus(auditEvent, auditEventDTO.getStatusId());

        save(auditEvent);
        setEventData(auditEvent, auditEventDTO.getEventData());
        return auditEvent;
    }
    
}
