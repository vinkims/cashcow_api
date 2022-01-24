package com.example.cashcow_api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.cow.CowServiceTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECowServiceType;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowServiceType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CCowServiceType {
    
    @Autowired
    private ICowServiceType sServiceType;

    @PostMapping(path = "/cow/service/type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createServiceType(@Valid @RequestBody CowServiceTypeDTO serviceTypeDTO){

        ECowServiceType serviceType = sServiceType.create(serviceTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created service type", new CowServiceTypeDTO(serviceType)));
    }

    @GetMapping(path = "/cow/service/type/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getServiceTypeById(@PathVariable Integer id){

        Optional<ECowServiceType> serviceType = sServiceType.getById(id);
        if (!serviceType.isPresent()){
            throw new NotFoundException("service type with specified id not found", "serviceTypeId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched service type", new CowServiceTypeDTO(serviceType.get())));
    }

    @GetMapping(path = "/cow/service/type", produces = "application/json")
    public ResponseEntity<SuccessResponse> getServiceTypeList(){

        List<ECowServiceType> serviceTypes = sServiceType.getAll();

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched list of service types", serviceTypes));
    }
}
