package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EExpense;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.expense.IExpense;

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
@RequestMapping(path = "/expense")
public class CExpense {
    
    @Autowired
    private IExpense sExpense;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createExpense(@RequestBody ExpenseDTO expenseDTO) throws URISyntaxException{

        EExpense expense = sExpense.create(expenseDTO);

        return ResponseEntity
            .created(new URI("/" + expense.getId()))
            .body(new SuccessResponse(201, "successfully created expense record", new ExpenseDTO(expense)));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getExpenseById(@PathVariable Integer id){

        EExpense expense = sExpense.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched expense record", new ExpenseDTO(expense)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);
        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("farm.id", "createdOn", "expenseType.id", "status.id")
        );
        Page<EExpense> expensePage = sExpense.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched expenses list", expensePage, 
                ExpenseDTO.class, EExpense.class));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateExpense(@PathVariable Integer id, @RequestBody ExpenseDTO expenseDTO) {

        EExpense expense = sExpense.update(id, expenseDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated expense", new ExpenseDTO(expense)));
    }
}
