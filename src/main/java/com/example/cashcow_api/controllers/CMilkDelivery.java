package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkDeliveryDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkDelivery;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.milk.IMilkDelivery;

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
public class CMilkDelivery {
    
    @Autowired
    private IMilkDelivery sMilkDelivery;

    @PostMapping(path = "/milk/delivery", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createDeliveryRecord(@RequestBody MilkDeliveryDTO deliveryDTO){

        EMilkDelivery delivery = sMilkDelivery.create(deliveryDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created delivery record", new MilkDeliveryDTO(delivery)));
    }

    @GetMapping(path = "/milk/delivery/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getDeliveryById(@PathVariable Integer id){

        Optional<EMilkDelivery> delivery = sMilkDelivery.getById(id);
        if (!delivery.isPresent()){
            throw new NotFoundException("delivery with specified id not found", "deliveryId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "delivery record fetched", new MilkDeliveryDTO(delivery.get())));
    }

    @GetMapping(path = "/milk/delivery", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("user.id", "shop.id", "shop.name")
        );

        Page<EMilkDelivery> page = sMilkDelivery.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched milk delivery list", page, MilkDeliveryDTO.class, EMilkDelivery.class));
    }
}
