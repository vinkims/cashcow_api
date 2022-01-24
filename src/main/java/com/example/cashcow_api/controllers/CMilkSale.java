package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkSale;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.milk.IMilkSale;

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
public class CMilkSale {
    
    @Autowired
    private IMilkSale sMilkSale;

    @PostMapping(path = "/milk/sale", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createSaleRecord(@RequestBody MilkSaleDTO saleDTO){

        EMilkSale milkSale = sMilkSale.create(saleDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created sale record", new MilkSaleDTO(milkSale)));
    }

    @GetMapping(path = "/milk/sale/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getSaleById(@PathVariable Integer id){

        Optional<EMilkSale> sale = sMilkSale.getById(id);
        if (!sale.isPresent()){
            throw new NotFoundException("sale with specified id not found", "saleId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "milk sale record fetched", new MilkSaleDTO(sale.get())));
    }

    @GetMapping(path = "/milk/sale", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("attendant.id","customer.id", "shop.id", "shop.name", "status.id", "status.name")
        );

        Page<EMilkSale> page = sMilkSale.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched milk sale list", page, MilkSaleDTO.class, EMilkSale.class));
    }
}
