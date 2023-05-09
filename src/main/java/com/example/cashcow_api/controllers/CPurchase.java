package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.purchases.PurchaseDTO;
import com.example.cashcow_api.models.EPurchase;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.purchase.IPurchase;

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
@RequestMapping(path = "/purchase")
public class CPurchase {
    
    @Autowired
    private IPurchase sPurchase;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createPurchaseRecord(@RequestBody PurchaseDTO purchaseDTO) throws URISyntaxException{

        EPurchase purchase = sPurchase.create(purchaseDTO);

        return ResponseEntity
            .created(new URI("/" + purchase.getId()))
            .body(new SuccessResponse(201, "successfully created purchase record", new PurchaseDTO(purchase)));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getById(@PathVariable Integer id){

        EPurchase purchase = sPurchase.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched purchase record", new PurchaseDTO(purchase)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("createdOn", "supplier.id", "updatedOn", "farm.id", "status.id")
        );

        Page<EPurchase> page = sPurchase.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched purchase list", page, 
                PurchaseDTO.class, EPurchase.class));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updatePurchase(@PathVariable Integer id, @RequestBody PurchaseDTO purchaseDTO) {

        EPurchase purchase = sPurchase.update(id, purchaseDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated purchase", new PurchaseDTO(purchase)));
    }
}
