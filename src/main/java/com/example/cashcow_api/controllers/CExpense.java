package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EExpense;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.expense.IExpense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CExpense {
    
    @Autowired
    private IExpense sExpense;

    @PostMapping(path = "/expense", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createExpense(@RequestBody ExpenseDTO expenseDTO) throws URISyntaxException{

        EExpense expense = sExpense.create(expenseDTO);

        return ResponseEntity
            .created(new URI(String.format("/expense/%s", expense.getId())))
            .body(new SuccessResponse(201, "successfully created expense record", new ExpenseDTO(expense)));
    }

    @GetMapping(path = "/expense/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getExpenseById(@PathVariable Integer id){

        Optional<EExpense> expense = sExpense.getById(id);
        if (!expense.isPresent()){
            throw new NotFoundException("expense with specified id not found", "expenseId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched expense record", new ExpenseDTO(expense.get())));
    }

    @GetMapping(path = "/expense", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);
        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("cow.id", "createdOn", "expenseType.id", "status.id", "user.id")
        );
        Page<EExpense> expensePage = sExpense.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched expenses list", expensePage, ExpenseDTO.class, EExpense.class));
    }
}
