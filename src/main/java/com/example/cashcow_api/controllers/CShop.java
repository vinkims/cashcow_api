package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.shop.IShop;

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
@RequestMapping(path = "/shop")
public class CShop {
    
    @Autowired
    private IShop sShop;

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createShop(@Valid @RequestBody ShopDTO shopDTO) throws URISyntaxException{

        EShop shop = sShop.create(shopDTO);

        return ResponseEntity
            .created(new URI("/" + shop.getId()))
            .body(new SuccessResponse(201, "shop created successfully", new ShopDTO(shop)));
    }

    @GetMapping(path = "/{shopId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getShopById(@PathVariable Integer shopId){

        EShop shop = sShop.getById(shopId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "shop fetched", new ShopDTO(shop)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "updatedOn", "farm.id", "status.id"));

        Page<EShop> shops = sShop.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched list of shops", shops, ShopDTO.class, EShop.class));
    }

    @PatchMapping(path = "/{shopId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateShop(@PathVariable Integer shopId, @RequestBody ShopDTO shopDTO) {

        EShop shop = sShop.update(shopId, shopDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated shop", new ShopDTO(shop)));
    }
}
