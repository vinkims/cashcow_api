package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionDTO;
import com.example.cashcow_api.models.EMilkConsumption;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.milk.IMilkConsumption;

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
@RequestMapping(path = "/milk/consumption")
public class CMilkConsumption {
    
    @Autowired
    private IMilkConsumption sMilkConsumption;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createConsumptionRecord(@RequestBody MilkConsumptionDTO consumptionDTO) throws URISyntaxException{

        EMilkConsumption consumption = sMilkConsumption.create(consumptionDTO);

        return ResponseEntity
            .created(new URI("/" + consumption.getId()))
            .body(new SuccessResponse(201, "successfylly created consumption record", new MilkConsumptionDTO(consumption)));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getConsumptionById(@PathVariable Integer id){

        EMilkConsumption consumption = sMilkConsumption.getById(id, true);
        
        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "returned consumption record", new MilkConsumptionDTO(consumption)));    
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);
        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList(
                "user.id", "session.id", "category.id", "createdOn", "quantity", "unitCost", "farm.id", "status.id", "updatedOn")
        );

        Page<EMilkConsumption> page = sMilkConsumption.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched consumption list", page, 
                MilkConsumptionDTO.class, EMilkConsumption.class));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateMilkConsumption(@PathVariable Integer id, @RequestBody MilkConsumptionDTO consumptionDTO) {

        EMilkConsumption consumption = sMilkConsumption.update(id, consumptionDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated milk consumption", new MilkConsumptionDTO(consumption)));
    }

}
