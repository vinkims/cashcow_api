package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.cow.CowServiceTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowServiceType;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowServiceType;

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

@RestController
@RequestMapping(path = "/cow/service/type")
public class CCowServiceType {
    
    @Autowired
    private ICowServiceType sServiceType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createServiceType(@Valid @RequestBody CowServiceTypeDTO serviceTypeDTO) throws URISyntaxException{

        ECowServiceType serviceType = sServiceType.create(serviceTypeDTO);

        return ResponseEntity
            .created(new URI("/" + serviceType.getId()))
            .body(new SuccessResponse(201, "successfully created service type", new CowServiceTypeDTO(serviceType)));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getServiceTypeById(@PathVariable Integer id){

        ECowServiceType serviceType = sServiceType.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched service type", new CowServiceTypeDTO(serviceType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "farm.id", "farm.name", "status.id"));

        Page<ECowServiceType> serviceTypes = sServiceType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched list of service types", serviceTypes, 
                CowServiceTypeDTO.class, ECowServiceType.class));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateServiceType(@PathVariable Integer id, @RequestBody CowServiceTypeDTO serviceTypeDTO) {

        ECowServiceType serviceType = sServiceType.update(id, serviceTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated service type", new CowServiceTypeDTO(serviceType)));

    }
}
