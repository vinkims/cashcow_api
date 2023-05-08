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

import com.example.cashcow_api.dtos.cow.CowBreedingDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowBreeding;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowBreeding;

@RestController
@RequestMapping(path = "/cow/breeding")
public class CCowBreeding {
    
    @Autowired
    private ICowBreeding sCowBreeding;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCowBreeding(@RequestBody CowBreedingDTO cowBreedingDTO) throws URISyntaxException {

        ECowBreeding cowBreeding = sCowBreeding.create(cowBreedingDTO);

        return ResponseEntity
            .created(new URI("/" + cowBreeding.getId()))
            .body(new SuccessResponse(201, "successfully created cow breeding", new CowBreedingDTO(cowBreeding)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList(
            "createdOn", "updatedOn", "breedingType.id", "cow.id", "bull.id", "status.id", "cow.farm.id"
        ));

        Page<ECowBreeding> breedings = sCowBreeding.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched cow breedings", breedings, 
                CowBreedingDTO.class, ECowBreeding.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCowBreeding(@PathVariable Integer id) {

        ECowBreeding cowBreeding = sCowBreeding.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched cow breedings", new CowBreedingDTO(cowBreeding)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCowBreeding(@PathVariable Integer id, @RequestBody CowBreedingDTO cowBreedingDTO) {

        ECowBreeding cowBreeding = sCowBreeding.update(id, cowBreedingDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated cow breeding", new CowBreedingDTO(cowBreeding)));
    }
}