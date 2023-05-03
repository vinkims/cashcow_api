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

import com.example.cashcow_api.dtos.cow.BreedDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EBreed;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.IBreed;

@RestController
@RequestMapping(path = "/breed")
public class CBreed {

    @Autowired
    private IBreed sCowBreed;
    
    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createBreed(@RequestBody BreedDTO cowBreedDTO) throws URISyntaxException {

        EBreed cowBreed = sCowBreed.create(cowBreedDTO);

        return ResponseEntity
            .created(new URI("/" + cowBreed.getId()))
            .body(new SuccessResponse(200, "successfully created cow breed", new BreedDTO(cowBreed)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "status.id"));

        Page<EBreed> cowBreeds = sCowBreed.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched cow breeds", cowBreeds, 
                BreedDTO.class, EBreed.class));
    }

    @GetMapping(path = "/{breedId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getBreed(@PathVariable Integer breedId) {

        EBreed cowBreed = sCowBreed.getById(breedId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched cow breed", new BreedDTO(cowBreed)));
    }

    @PatchMapping(path = "/{breedId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateBreed(@PathVariable Integer breedId, @RequestBody BreedDTO cowBreedDTO) {

        EBreed cowBreed = sCowBreed.update(breedId, cowBreedDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated cow breed", new BreedDTO(cowBreed)));
    }
}
