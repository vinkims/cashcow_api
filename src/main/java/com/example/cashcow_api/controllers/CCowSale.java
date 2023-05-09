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

import com.example.cashcow_api.dtos.cow.CowSaleDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowSale;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.cow.ICowSale;

@RestController
@RequestMapping(path = "/cow/sale")
public class CCowSale {
    
    @Autowired
    private ICowSale sCowSale;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCowSale(@RequestBody CowSaleDTO cowSaleDTO) throws URISyntaxException {

        ECowSale cowSale = sCowSale.create(cowSaleDTO);

        return ResponseEntity
            .created(new URI("/" + cowSale.getId()))
            .body(new SuccessResponse(201, "successfully created cow sale", new CowSaleDTO(cowSale)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList(
            "createdOn", "updatedOn", "cow.id", "buyer.id", "status.id", "cow.farm.id"));

        Page<ECowSale> cowSales = sCowSale.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched cow sales", cowSales, 
                CowSaleDTO.class, ECowSale.class));
    }

    @GetMapping(path = "/{saleId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCowSale(@PathVariable Integer saleId) {

        ECowSale cowSale = sCowSale.getById(saleId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched cow sale", new CowSaleDTO(cowSale)));
    }

    @PatchMapping(path = "/{saleId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCowSale(@PathVariable Integer saleId, @RequestBody CowSaleDTO cowSaleDTO) {

        ECowSale cowSale = sCowSale.update(saleId, cowSaleDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated cow sale", new CowSaleDTO(cowSale)));
    }
}
