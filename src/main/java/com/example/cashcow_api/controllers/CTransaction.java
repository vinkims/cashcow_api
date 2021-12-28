package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ETransaction;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.transaction.ITransaction;

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
public class CTransaction {
    
    @Autowired
    private ITransaction sTransaction;

    @PostMapping(path = "/transaction", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createTransaction(@RequestBody TransactionDTO transactionDTO){

        ETransaction transaction = sTransaction.create(transactionDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "successfully created transaction", new TransactionDTO(transaction)));
    }

    @GetMapping(path = "/transaction/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getTransactionById(@PathVariable Integer id){

        Optional<ETransaction> transaction = sTransaction.getById(id);
        if (!transaction.isPresent()){
            throw new NotFoundException("transaction with specified id not found", "transactionId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "transaction fetched", new TransactionDTO(transaction.get())));
    }

    @GetMapping(path = "/transaction", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);
        
        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("attendant.id", "customer.id", "paymentChannel.id", "shop.id", "status.id", "transactionType.id")
        );

        Page<ETransaction> page = sTransaction.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched transactions list", page, TransactionDTO.class, ETransaction.class));
    }
}
