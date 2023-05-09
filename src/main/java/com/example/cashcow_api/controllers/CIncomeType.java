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

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.income.IncomeTypeDTO;
import com.example.cashcow_api.models.EIncomeType;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.income.IIncomeType;

@RestController
@RequestMapping(path = "/income/type")
public class CIncomeType {
    
    @Autowired
    private IIncomeType sIncomeType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createIncomeType(@RequestBody IncomeTypeDTO incomeTypeDTO) throws URISyntaxException {

        EIncomeType incomeType = sIncomeType.create(incomeTypeDTO);

        return ResponseEntity
            .created(new URI("/" + incomeType.getId()))
            .body(new SuccessResponse(201, "successfully created income type", new IncomeTypeDTO(incomeType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "updatedOn", "farm.id", "status.id"));

        Page<EIncomeType> incomeTypes = sIncomeType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched income types", incomeTypes, 
                IncomeTypeDTO.class, EIncomeType.class));
    }

    @GetMapping(path = "/{typeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getIncomeType(@PathVariable Integer typeId) {

        EIncomeType incomeType = sIncomeType.getById(typeId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched income type", new IncomeTypeDTO(incomeType)));
    }

    @PatchMapping(path = "/{typeId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateIncomeType(@PathVariable Integer typeId, @RequestBody IncomeTypeDTO incomeTypeDTO) {

        EIncomeType incomeType = sIncomeType.update(typeId, incomeTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated income type", new IncomeTypeDTO(incomeType)));
    }
}
