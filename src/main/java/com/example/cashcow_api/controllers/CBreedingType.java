package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import com.example.cashcow_api.dtos.breeding.BreedingTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EBreedingType;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.breeding.IBreedingType;

@RestController
@RequestMapping(path = "/breeding/type")
public class CBreedingType {
 
    @Autowired
    private IBreedingType sBreedingType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createBreedingType(@RequestBody BreedingTypeDTO breedingTypeDTO) throws URISyntaxException {

        EBreedingType breedingType = sBreedingType.create(breedingTypeDTO);

        return ResponseEntity
            .created(new URI("/" + breedingType.getId()))
            .body(new SuccessResponse(201, "successfully created breeding type", new BreedingTypeDTO(breedingType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        List<String> allowedFields = new ArrayList<>();

        Page<EBreedingType> breedingTypes = sBreedingType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched breeding types", breedingTypes, 
                BreedingTypeDTO.class, EBreedingType.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getBreedingType(@PathVariable Integer id) {

        EBreedingType breedingType = sBreedingType.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched breeding type", new BreedingTypeDTO(breedingType)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateBreedingType(@PathVariable Integer id, @RequestBody BreedingTypeDTO breedingTypeDTO) {

        EBreedingType breedingType = sBreedingType.update(id, breedingTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated breeding type", new BreedingTypeDTO(breedingType)));
    }
}
