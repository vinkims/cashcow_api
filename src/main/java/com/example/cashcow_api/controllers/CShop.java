package com.example.cashcow_api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.shop.IShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CShop {
    
    @Autowired
    private IShop sShop;

    @PostMapping(path = "/shop", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createShop(@Valid @RequestBody ShopDTO shopDTO){

        EShop shop = sShop.create(shopDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "shop created successfully", new ShopDTO(shop)));
    }

    @GetMapping(path = "/shop/{shopId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getShopById(@PathVariable Integer shopId){

        Optional<EShop> shop = sShop.getById(shopId);
        if (!shop.isPresent()){
            throw new NotFoundException("shop with specified id not found", "shopId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "shop fetched", new ShopDTO(shop.get())));
    }

    @GetMapping(path = "/shop", produces = "application/json")
    public ResponseEntity<SuccessResponse> getShopList(){

        List<EShop> shops = sShop.getAll();

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched list of shops", shops));
    }
}
