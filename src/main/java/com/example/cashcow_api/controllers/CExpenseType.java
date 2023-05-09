package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.expense.ExpenseTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EExpenseType;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.expense.IExpenseType;

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
@RequestMapping(path = "/expense/type")
public class CExpenseType {
    
    @Autowired
    private IExpenseType sExpenseType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createExpenseType(@Valid @RequestBody ExpenseTypeDTO expenseTypeDTO) throws URISyntaxException{

        EExpenseType expenseType = sExpenseType.create(expenseTypeDTO);

        return ResponseEntity
            .created(new URI("/" + expenseType.getId()))
            .body(new SuccessResponse(201, "successfully created expense type", new ExpenseTypeDTO(expenseType)));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getExpenseTypeById(@PathVariable Integer id){

        EExpenseType expenseType = sExpenseType.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched expense type", new ExpenseTypeDTO(expenseType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException{
        
        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "updatedOn", "farm.id", "status.id"));

        Page<EExpenseType> expenseTypes = sExpenseType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched expense type list", expenseTypes, 
                ExpenseTypeDTO.class, EExpenseType.class));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateExpenseType(@PathVariable Integer id, @RequestBody ExpenseTypeDTO expenseTypeDTO) {

        EExpenseType expenseType = sExpenseType.update(id, expenseTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated expense type", new ExpenseTypeDTO(expenseType)));
    }
}
