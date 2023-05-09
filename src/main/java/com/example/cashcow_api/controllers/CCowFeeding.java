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

import com.example.cashcow_api.dtos.cow.CowFeedingDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowFeeding;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowFeeding;

@RestController
@RequestMapping(path = "/cow/feeding")
public class CCowFeeding {
    
    @Autowired
    private ICowFeeding sCowFeeding;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCowFeeding(@RequestBody CowFeedingDTO cowFeedingDTO) throws URISyntaxException {

        ECowFeeding cowFeeding = sCowFeeding.create(cowFeedingDTO);

        return ResponseEntity
            .created(new URI("/" + cowFeeding.getId()))
            .body(new SuccessResponse(201, "successfully created cow feeding", new CowFeedingDTO(cowFeeding)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "farm.id", "status.id", "updatedOn", "user.id"));

        Page<ECowFeeding> cowFeedings = sCowFeeding.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched cow feedings", cowFeedings, 
                CowFeedingDTO.class, ECowFeeding.class));
    }

    @GetMapping(path = "/{feedingId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCowFeeding(@PathVariable Integer feedingId) {

        ECowFeeding cowFeeding = sCowFeeding.getById(feedingId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched cow feeding", new CowFeedingDTO(cowFeeding)));
    }

    @PatchMapping(path = "/{feedingId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCowFeeding(@PathVariable Integer feedingId, @RequestBody CowFeedingDTO cowFeedingDTO) {

        ECowFeeding cowFeeding = sCowFeeding.update(feedingId, cowFeedingDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated cow feeding", new CowFeedingDTO(cowFeeding)));
    }
}
