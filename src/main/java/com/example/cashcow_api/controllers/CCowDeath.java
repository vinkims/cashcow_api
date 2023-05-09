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

import com.example.cashcow_api.dtos.cow.CowDeathDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowDeath;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowDeath;

@RestController
@RequestMapping(path = "/cow/death")
public class CCowDeath {
    
    @Autowired
    private ICowDeath sCowDeath;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCowDeath(@RequestBody CowDeathDTO cowDeathDTO) throws URISyntaxException {

        ECowDeath cowDeath = sCowDeath.create(cowDeathDTO);

        return ResponseEntity
            .created(new URI("/" + cowDeath.getId()))
            .body(new SuccessResponse(201, "successfully created cow death", new CowDeathDTO(cowDeath)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList(
            "cow.id", "createdOn", "status.id", "updatedOn", "cow.farm.id"));

        Page<ECowDeath> deaths = sCowDeath.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched cow deaths", deaths, 
                CowDeathDTO.class, ECowDeath.class));
    }

    @GetMapping(path = "/{deathId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCowDeath(@PathVariable Integer deathId) {

        ECowDeath cowDeath = sCowDeath.getById(deathId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched cow death", new CowDeathDTO(cowDeath)));
    }

    @PatchMapping(path = "/{deathId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCowDeath(@PathVariable Integer deathId, @RequestBody CowDeathDTO cowDeathDTO) {

        ECowDeath cowDeath = sCowDeath.update(deathId, cowDeathDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated cow death", new CowDeathDTO(cowDeath)));
    }
}
