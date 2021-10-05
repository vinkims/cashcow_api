package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkProduction;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.milk.IMilkProduction;

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
public class CMilkProduction {
    
    @Autowired
    private IMilkProduction sMilkProduction;

    @PostMapping(path = "/milk/production", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createProductionRecord(@RequestBody MilkProductionDTO productionDTO){

        EMilkProduction production = sMilkProduction.create(productionDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created production record", new MilkProductionDTO(production)));
    }

    @GetMapping(path = "/milk/production/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getProductionById(@PathVariable Integer id){

        Optional<EMilkProduction> production = sMilkProduction.getById(id);
        if (!production.isPresent()){
            throw new NotFoundException("milk production record with specified id not found", "productionId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "milk production record fetched", new MilkProductionDTO(production.get())));
    }

    @GetMapping(path = "/milk/production", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("createdOn","cow.id", "cow.name", "milkingSession.id", "milkingSession.name", "user.id")
        );

        Page<EMilkProduction> page = sMilkProduction.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched prodcution list", page, MilkProductionDTO.class, EMilkProduction.class));
    }
}
