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

import com.example.cashcow_api.dtos.cow.CowPurchaseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowPurchase;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowPurchase;

@RestController
@RequestMapping(path = "/cow/purchase")
public class CCowPurchase {
    
    @Autowired
    private ICowPurchase sCowPurchase;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCowPurchase(@RequestBody CowPurchaseDTO cowPurchaseDTO) throws URISyntaxException {

        ECowPurchase cowPurchase = sCowPurchase.create(cowPurchaseDTO);

        return ResponseEntity
            .created(new URI("/" + cowPurchase.getId()))
            .body(new SuccessResponse(201, "successfully created cow purchase", new CowPurchaseDTO(cowPurchase)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "updatedOn", "cow.id", "cow.farm.id", "status.id"));

        Page<ECowPurchase> purchases = sCowPurchase.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched cow purchases", purchases, 
                CowPurchaseDTO.class, ECowPurchase.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCowPurchase(@PathVariable Integer id) {

        ECowPurchase cowPurchase = sCowPurchase.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched cow purchase", new CowPurchaseDTO(cowPurchase)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCowPurchase(@PathVariable Integer id, @RequestBody CowPurchaseDTO cowPurchaseDTO) {

        ECowPurchase cowPurchase = sCowPurchase.update(id, cowPurchaseDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated cow purchase", new CowPurchaseDTO(cowPurchase)));
    }
}
