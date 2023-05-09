package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkShopDeliveryDTO;
import com.example.cashcow_api.models.EMilkShopDelivery;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.milk.IMilkShopDelivery;

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
@RequestMapping(path = "/milk/shop_delivery")
public class CMilkShopDelivery {
    
    @Autowired
    private IMilkShopDelivery sMilkDelivery;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createDeliveryRecord(@RequestBody MilkShopDeliveryDTO deliveryDTO) throws URISyntaxException{

        EMilkShopDelivery delivery = sMilkDelivery.create(deliveryDTO);

        return ResponseEntity
            .created(new URI("/" + delivery.getId()))
            .body(new SuccessResponse(201, "successfully created delivery record", new MilkShopDeliveryDTO(delivery)));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getDeliveryById(@PathVariable Integer id){

        EMilkShopDelivery delivery = sMilkDelivery.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "delivery record fetched", new MilkShopDeliveryDTO(delivery)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("user.id", "shop.id", "shop.name", "shop.farm.id", "status.id")
        );

        Page<EMilkShopDelivery> page = sMilkDelivery.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched milk delivery list", page, 
                MilkShopDeliveryDTO.class, EMilkShopDelivery.class));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateDelivery(@PathVariable Integer id, @RequestBody MilkShopDeliveryDTO deliveryDTO) {

        EMilkShopDelivery delivery = sMilkDelivery.update(id, deliveryDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated milk shop delivery", new MilkShopDeliveryDTO(delivery)));
    }
}
