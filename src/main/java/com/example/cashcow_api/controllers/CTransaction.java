package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.models.ETransaction;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.transaction.ITransaction;

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
@RequestMapping(path = "/transaction")
public class CTransaction {
    
    @Autowired
    private ITransaction sTransaction;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createTransaction(@RequestBody TransactionDTO transactionDTO) throws URISyntaxException{

        ETransaction transaction = sTransaction.create(transactionDTO);

        return ResponseEntity
            .created(new URI("/" + transaction.getId()))
            .body(new SuccessResponse(201, "successfully created transaction", new TransactionDTO(transaction)));
    }

    @GetMapping(path = "/expense", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getExpenses(@RequestParam Map<String, Object> params) 
        throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
        InvocationTargetException, NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        Page<ETransaction> expenses = sTransaction.getAllExpenses(pageDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched all expenses", expenses, 
                TransactionDTO.class, ETransaction.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getTransactionById(@PathVariable Integer id){

        ETransaction transaction = sTransaction.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "transaction fetched", new TransactionDTO(transaction)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);
        
        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("user.id", "paymentChannel.id", "shop.id", "transactionType.id", "farm.id", "transactionCode")
        );

        Page<ETransaction> page = sTransaction.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched transactions list", page, 
                TransactionDTO.class, ETransaction.class));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "appliction/json")
    public ResponseEntity<SuccessResponse> updateTransaction(@PathVariable Integer id, @RequestBody TransactionDTO transactionDTO) {

        ETransaction transaction = sTransaction.update(id, transactionDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated transaction", new TransactionDTO(transaction)));
    }
}
