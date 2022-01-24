package com.example.cashcow_api.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.expense.ExpenseTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EExpenseType;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.expense.IExpenseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CExpenseType {
    
    @Autowired
    private IExpenseType sExpenseType;

    @PostMapping(path = "/expense/type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createExpenseType(@Valid @RequestBody ExpenseTypeDTO expenseTypeDTO) throws URISyntaxException{

        EExpenseType expenseType = sExpenseType.create(expenseTypeDTO);

        return ResponseEntity
            .created(new URI(String.format("/expense/type/%s", expenseType.getId())))
            .body(new SuccessResponse(201, "successfully created expense type", new ExpenseTypeDTO(expenseType)));
    }

    @GetMapping(path = "/expense/type/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getExpenseTypeById(@PathVariable Integer id){

        Optional<EExpenseType> expenseType = sExpenseType.getById(id);
        if (!expenseType.isPresent()){
            throw new NotFoundException("expense type with specified id not found", "expenseTypeId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched expense type", new ExpenseTypeDTO(expenseType.get())));
    }

    @GetMapping(path = "/expense/type", produces = "application/json")
    public ResponseEntity<SuccessResponse> getExpenseTypeList(){

        List<EExpenseType> expenseTypes = sExpenseType.getAll();

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched expense type list", expenseTypes));
    }
}
