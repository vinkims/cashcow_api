package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cashcow_api.dtos.audit_event.AuditEventDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EAuditEvent;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.audit_event.IAuditEvent;

@RestController
@RequestMapping(path = "/audit/event")
public class CAuditEvent {
    
    @Autowired
    private IAuditEvent sAuditEvent;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createAuditEvent(@RequestBody AuditEventDTO auditEventDTO) throws URISyntaxException {

        EAuditEvent auditEvent = sAuditEvent.create(auditEventDTO);

        return ResponseEntity
            .created(new URI("/" + auditEvent.getId()))
            .body(new SuccessResponse(200, "successfully created audit event", new AuditEventDTO(auditEvent)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("farm.id", "eventType.id", "status.id", "createdOn"));

        Page<EAuditEvent> auditEvents = sAuditEvent.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched audit events", auditEvents, 
                AuditEventDTO.class, EAuditEvent.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getAuditEvent(@PathVariable Integer id) {

        EAuditEvent auditEvent = sAuditEvent.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched audit event", new AuditEventDTO(auditEvent)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateAuditEvent(@PathVariable Integer id, @RequestBody AuditEventDTO auditEventDTO) {

        EAuditEvent auditEvent = sAuditEvent.update(id, auditEventDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated audit event", new AuditEventDTO(auditEvent)));
    }
}
