package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionCategoryDTO;
import com.example.cashcow_api.models.EMilkConsumptionCategory;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.milk.IMilkConsumptionCategory;

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
@RequestMapping(path = "/milk/consumption/category")
public class CMilkConsumptionCategory {
    
    @Autowired
    private IMilkConsumptionCategory sCategory;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createConsumptionCategory(@RequestBody MilkConsumptionCategoryDTO categoryDTO) throws URISyntaxException {

        EMilkConsumptionCategory category = sCategory.create(categoryDTO);

        return ResponseEntity
            .created(new URI("/" + category.getId()))
            .body(new SuccessResponse(201, "successfully created consumption category", new MilkConsumptionCategoryDTO(category)));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCategoryById(@PathVariable Integer id) {

        EMilkConsumptionCategory category = sCategory.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched consumption category", new MilkConsumptionCategoryDTO(category)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "updatedOn", "farm.id", "status.id"));

        Page<EMilkConsumptionCategory> categories = sCategory.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched categories", categories, 
                MilkConsumptionCategoryDTO.class, EMilkConsumptionCategory.class));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateConsumptionCategory(@PathVariable Integer id, @RequestBody MilkConsumptionCategoryDTO categoryDTO) {

        EMilkConsumptionCategory category = sCategory.update(id, categoryDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated milk consumption category", new MilkConsumptionCategoryDTO(category)));
    }
}
