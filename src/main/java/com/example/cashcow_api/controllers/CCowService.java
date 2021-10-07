package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowServiceDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECowService;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CCowService {
    

    @Autowired
    private ICowService sCowService;

    @PostMapping(path = "/cow/service", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createService(@RequestBody CowServiceDTO serviceDTO){

        ECowService service = sCowService.create(serviceDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created cow service", new CowServiceDTO(service)));
    }

    @GetMapping(path = "/cow/service", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("cow.id", "cowServiceType.id", "user.id")
        );

        Page<ECowService> page = sCowService.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched paginated services list", page, CowServiceDTO.class, ECowService.class));
    }

    @GetMapping(path = "/cow/service/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getServiceById(@PathVariable Integer id){

        Optional<ECowService> service = sCowService.getById(id);
        if (!service.isPresent()){
            throw new NotFoundException("service with specified id not found", "serviceId");
        }
        
        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched service type", new CowServiceDTO(service.get())));
    }
}
