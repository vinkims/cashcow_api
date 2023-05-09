package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.example.cashcow_api.dtos.transaction.TransactionTypeDTO;
import com.example.cashcow_api.models.ETransactionType;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.transaction.ITransactionType;

@RestController
@RequestMapping(path = "/transaction/type")
public class CTransactionType {
    
    @Autowired
    private ITransactionType sTransactionType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createTransactiontype(@RequestBody TransactionTypeDTO transactionTypeDTO) throws URISyntaxException {

        ETransactionType transactionType = sTransactionType.create(transactionTypeDTO);

        return ResponseEntity
            .created(new URI("/" + transactionType.getId()))
            .body(new SuccessResponse(201, "successfully created transaction type", new TransactionTypeDTO(transactionType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        List<String> allowedFields = new ArrayList<>();

        Page<ETransactionType> transactionTypes = sTransactionType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched transaction types", transactionTypes, 
                TransactionTypeDTO.class, ETransactionType.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getTransactiontype(@PathVariable Integer id) {

        ETransactionType transactionType = sTransactionType.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched transaction type", new TransactionTypeDTO(transactionType)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateTransactionType(@PathVariable Integer id, @RequestBody TransactionTypeDTO transactionTypeDTO) {

        ETransactionType transactionType = sTransactionType.update(id, transactionTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated transaction type", new TransactionTypeDTO(transactionType)));
    }
}
