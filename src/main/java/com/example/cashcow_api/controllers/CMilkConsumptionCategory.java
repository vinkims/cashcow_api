package com.example.cashcow_api.controllers;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.milk.MilkConsumptionCategoryDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkConsumptionCategory;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.milk.IMilkConsumptionCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CMilkConsumptionCategory {
    
    @Autowired
    private IMilkConsumptionCategory sCategory;

    @PostMapping(path = "/milk/consumption/category", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createConsumptionCategory(@RequestBody MilkConsumptionCategoryDTO categoryDTO){

        EMilkConsumptionCategory category = sCategory.create(categoryDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created consumption category", new MilkConsumptionCategoryDTO(category)));
    }

    @GetMapping(path = "/milk/consumption/category/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCategoryById(@PathVariable Integer id){

        Optional<EMilkConsumptionCategory> category = sCategory.getById(id);
        if (!category.isPresent()){
            throw new NotFoundException("category with specified id not found", "catagoryId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched consumption category", new MilkConsumptionCategoryDTO(category.get())));
    }

    @GetMapping(path = "/milk/consumption/category", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCategories(){

        List<EMilkConsumptionCategory> categories = sCategory.getAll();

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched categories", categories));
    }
}
