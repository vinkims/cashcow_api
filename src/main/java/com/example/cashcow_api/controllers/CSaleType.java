package com.example.cashcow_api.controllers;

import com.example.cashcow_api.responses.SuccessPaginatedResponse;

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

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.sales.SaleTypeDTO;
import com.example.cashcow_api.models.ESaleType;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.sale.ISaleType;

@RestController
@RequestMapping(path = "/sale/type")
public class CSaleType {
    
    @Autowired
    private ISaleType sSaleType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createSaleType(@RequestBody SaleTypeDTO saleTypeDTO) throws URISyntaxException {

        ESaleType saleType = sSaleType.create(saleTypeDTO);

        return ResponseEntity
            .created(new URI("/" + saleType.getId()))
            .body(new SuccessResponse(201, "successfully created sale type", new SaleTypeDTO(saleType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        List<String> allowedFields = new ArrayList<>();

        Page<ESaleType> saleTypes = sSaleType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched sale types", saleTypes, 
                SaleTypeDTO.class, ESaleType.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getSaleType(@PathVariable Integer id) {

        ESaleType saleType = sSaleType.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched sale type", new SaleTypeDTO(saleType)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateSaleType(@PathVariable Integer id, @RequestBody SaleTypeDTO saleTypeDTO) {

        ESaleType saleType = sSaleType.update(id, saleTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated sale type", new SaleTypeDTO(saleType)));
    }
}
