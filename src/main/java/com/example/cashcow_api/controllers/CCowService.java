package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.example.cashcow_api.dtos.cow.CowServiceDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowService;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowService;

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
@RequestMapping(path = "/cow/service")
public class CCowService {
    
    @Autowired
    private ICowService sCowService;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createService(@RequestBody CowServiceDTO serviceDTO) throws URISyntaxException{

        ECowService service = sCowService.create(serviceDTO);

        return ResponseEntity
            .created(new URI("/" + service.getId()))
            .body(new SuccessResponse(201, "successfully created cow service", new CowServiceDTO(service)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("cow.id", "cowServiceType.id", "user.id", "createdOn", "updatedOn", "status.id", "cost", "cow.farm.id")
        );

        Page<ECowService> page = sCowService.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched paginated services list", page, 
                CowServiceDTO.class, ECowService.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getServiceById(@PathVariable Integer id){

        ECowService service = sCowService.getById(id, true);
        
        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched service type", new CowServiceDTO(service)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateService(@PathVariable Integer id, @RequestBody CowServiceDTO serviceDTO) {
        
        ECowService service = sCowService.update(id, serviceDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated service", new CowServiceDTO(service)));
    }
}
