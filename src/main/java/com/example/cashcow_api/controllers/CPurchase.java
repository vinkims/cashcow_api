package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.purchases.PurchaseDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EPurchase;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.purchase.IPurchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CPurchase {
    
    @Autowired
    private IPurchase sPurchase;

    @PostMapping(path = "/purchase", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createPurchaseRecord(@RequestBody PurchaseDTO purchaseDTO){

        EPurchase purchase = sPurchase.create(purchaseDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created purchase record", new PurchaseDTO(purchase)));
    }

    @GetMapping(path = "/purchase/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getById(@PathVariable Integer id){

        Optional<EPurchase> purchase = sPurchase.getById(id);
        if (!purchase.isPresent()){
            throw new NotFoundException("purchase with specified id not found", "purchaseId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched purchase record", new PurchaseDTO(purchase.get())));
    }

    @GetMapping(path = "/purchase", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("createdOn", "supplier.id")
        );

        Page<EPurchase> page = sPurchase.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched purchase list", page, PurchaseDTO.class, EPurchase.class));
    }
}
