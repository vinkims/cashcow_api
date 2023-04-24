package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cow")
public class CCow {
    
    @Autowired
    private ICow sCow;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCow(@Valid @RequestBody CowDTO cowDTO) throws URISyntaxException{

        ECow cow = sCow.create(cowDTO);

        return ResponseEntity
            .created(new URI("/" + cow.getId()))
            .body(new SuccessResponse(201, "successfully created cow", new CowDTO(cow)));
    }

    @GetMapping(path = "/{cowId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCowById(@PathVariable Integer cowId){

        ECow cow = sCow.getById(cowId, true);
        
        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "cow fetched", new CowDTO(cow)));
    }

    @GetMapping(path = "/gender/{gender}", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getCowsByGender(@PathVariable String gender) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        List<ECow> cows = sCow.getCowsByGender(gender);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "returned list of cows", cows, CowDTO.class, ECow.class));
    }

    @GetMapping(path = "", produces = "application/json")
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

    @PatchMapping(path = "/{cowId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCow(@PathVariable Integer cowId, @RequestBody CowDTO cowDTO){

        ECow cow = sCow.update(cowId, cowDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(202, "successfully updated cow", new CowDTO(cow)));
    }
}
