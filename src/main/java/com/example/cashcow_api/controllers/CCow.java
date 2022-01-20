package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CCow {
    
    @Autowired
    private ICow sCow;

    @PostMapping(path = "/cow", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCow(@Valid @RequestBody CowDTO cowDTO){

        ECow cow = sCow.create(cowDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created cow", new CowDTO(cow)));
    }

    @GetMapping(path = "/cow/{cowId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCowById(@PathVariable Integer cowId){

        Optional<ECow> cow = sCow.getById(cowId);
        if (!cow.isPresent()){
            throw new NotFoundException("cow with specified id not found", "cowId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "cow fetched", new CowDTO(cow.get())));
    }

    @GetMapping(path = "/cow/gender/{gender}", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getCowsByGender(@PathVariable String gender) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        List<ECow> cows = sCow.getCowsByGender(gender);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "returned list of cows", cows, CowDTO.class, ECow.class));
    }

    @GetMapping(path = "/cow", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("status.id", "status.name", "createdOn", "profile.breed", "category.id", "category.name", "profile.gender")
        );

        Page<ECow> cowPage = sCow.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched list of cows", cowPage, CowDTO.class, ECow.class));
    }

    @PatchMapping(path = "/cow/{cowId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCow(@PathVariable Integer cowId, @RequestBody CowDTO cowDTO){

        Optional<ECow> cow = sCow.getById(cowId);
        if (!cow.isPresent()){
            throw new NotFoundException("cow with specified id not found", "cowId");
        }

        ECow cowTemp = sCow.update(cow.get(), cowDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(202, "successfully updated cow", new CowDTO(cowTemp)));
    }
}
