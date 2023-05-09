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
import com.example.cashcow_api.dtos.income.IncomeDTO;
import com.example.cashcow_api.models.EIncome;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.income.IIncome;

@RestController
@RequestMapping(path = "/income")
public class CIncome {
    
    @Autowired
    private IIncome sIncome;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createIncome(@RequestBody IncomeDTO incomeDTO) throws URISyntaxException {

        EIncome income = sIncome.create(incomeDTO);

        return ResponseEntity
            .created(new URI("/" + income.getId()))
            .body(new SuccessResponse(201, "successfully created income", new IncomeDTO(income)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList(
            "createdOn", "updatedOn", "incomeType.id", "farm.id", "status.id"));

        Page<EIncome> incomes = sIncome.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched incomes", incomes, 
                IncomeDTO.class, EIncome.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getIncome(@PathVariable Integer id) {

        EIncome income = sIncome.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched income", new IncomeDTO(income)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateIncome(@PathVariable Integer id, @RequestBody IncomeDTO incomeDTO) {

        EIncome income = sIncome.update(id, incomeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated income", new IncomeDTO(income)));
    }
}
