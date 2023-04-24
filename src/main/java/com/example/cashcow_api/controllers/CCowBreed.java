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

import com.example.cashcow_api.dtos.cow.CowBreedDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowBreed;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowBreed;

@RestController
@RequestMapping(path = "/cow_breed")
public class CCowBreed {

    @Autowired
    private ICowBreed sCowBreed;
    
    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCowBreed(@RequestBody CowBreedDTO cowBreedDTO) throws URISyntaxException {

        ECowBreed cowBreed = sCowBreed.create(cowBreedDTO);

        return ResponseEntity
            .created(new URI("/" + cowBreed.getId()))
            .body(new SuccessResponse(200, "successfully created cow breed", new CowBreedDTO(cowBreed)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "status.id"));

        Page<ECowBreed> cowBreeds = sCowBreed.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched cow breeds", cowBreeds, 
                CowBreedDTO.class, ECowBreed.class));
    }

    @GetMapping(path = "/{breedId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCowBreed(@PathVariable Integer breedId) {

        ECowBreed cowBreed = sCowBreed.getById(breedId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched cow breed", new CowBreedDTO(cowBreed)));
    }

    @PatchMapping(path = "/{breedId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCowBreed(@PathVariable Integer breedId, @RequestBody CowBreedDTO cowBreedDTO) {

        ECowBreed cowBreed = sCowBreed.update(breedId, cowBreedDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated cow breed", new CowBreedDTO(cowBreed)));
    }
}
