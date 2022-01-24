package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.weight.WeightDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EWeight;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.weight.IWeight;

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
public class CWeight {
    
    @Autowired
    private IWeight sWeight;

    @PostMapping(path = "/weight", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createWeight(@RequestBody WeightDTO weightDTO) throws URISyntaxException{

        EWeight weight = sWeight.create(weightDTO);

        return ResponseEntity
            .created(new URI(String.format("/weight/%s", weight.getId())))
            .body(new SuccessResponse(201, "successfully created weight record", new WeightDTO(weight)));
    }

    @GetMapping(path = "/weight", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedWeightList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);
        ArrayList<String> allowableFields = new ArrayList<>(Arrays.asList("cow.id", "createdOn"));
        Page<EWeight> weightPage = sWeight.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched weight record list", weightPage, WeightDTO.class, EWeight.class));
    }

    @GetMapping(path = "/weight/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getWeightById(@PathVariable Integer id){
        
        Optional<EWeight> weight = sWeight.getById(id);
        if (!weight.isPresent()){
            throw new NotFoundException("record with specified id not found", "weightId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched weight record", new WeightDTO(weight.get())));
    }
}
