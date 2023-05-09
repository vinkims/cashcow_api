package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.cashcow_api.dtos.cow.CowWeightDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowWeight;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowWeight;

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
@RequestMapping(path = "/cow/weight")
public class CCowWeight {
    
    @Autowired
    private ICowWeight sCowWeight;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createWeight(@RequestBody CowWeightDTO cowWeightDTO) throws URISyntaxException {

        ECowWeight weight = sCowWeight.create(cowWeightDTO);

        return ResponseEntity
            .created(new URI("/" + weight.getId()))
            .body(new SuccessResponse(201, "successfully created weight record", new CowWeightDTO(weight)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedWeightList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList("cow.id", "createdOn", "updatedOn", "status.id", "cow.farm.id"));

        Page<ECowWeight> weightPage = sCowWeight.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched weight record list", weightPage, 
                CowWeightDTO.class, ECowWeight.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getWeightById(@PathVariable Integer id) {
        
        ECowWeight weight = sCowWeight.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched weight record", new CowWeightDTO(weight)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCowWeight(@PathVariable Integer id, @RequestBody CowWeightDTO cowWeightDTO) {

        ECowWeight weight = sCowWeight.update(id, cowWeightDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated cow weight", new CowWeightDTO(weight)));
    }
}
