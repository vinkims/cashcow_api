package com.example.cashcow_api.controllers;

import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import com.example.cashcow_api.dtos.contact.ContactTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EContactType;
import com.example.cashcow_api.services.contact.IContactType;

@RestController
@RequestMapping(path = "/contact/type")
public class CContactType {
    
    @Autowired
    private IContactType sContactType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createContactType(@RequestBody ContactTypeDTO contactTypeDTO) throws URISyntaxException {

        EContactType contactType = sContactType.create(contactTypeDTO);

        return ResponseEntity
            .created(new URI("/" + contactType.getId()))
            .body(new SuccessResponse(201, "successfully created contact type", new ContactTypeDTO(contactType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {
        
        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        List<String> allowedFields = new ArrayList<>();

        Page<EContactType> contactTypes = sContactType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched contact types", contactTypes, 
                ContactTypeDTO.class, EContactType.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getContactType(@PathVariable Integer id) {

        EContactType contactType = sContactType.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched contact types", new ContactTypeDTO(contactType)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateContactType(@PathVariable Integer id, @RequestBody ContactTypeDTO contactTypeDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EContactType contactType = sContactType.update(id, contactTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated contact type", new ContactTypeDTO(contactType)));
    }
}
