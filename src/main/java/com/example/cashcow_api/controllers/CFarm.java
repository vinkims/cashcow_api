package com.example.cashcow_api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.farm.IFarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CFarm {
    
    @Autowired
    private IFarm sFarm;

    @PostMapping(path = "/farm", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createFarm(@Valid @RequestBody FarmDTO farmDTO){

        EFarm farm = sFarm.create(farmDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(201, "farm successfully created", new FarmDTO(farm)));
    }

    @GetMapping(path = "/farm/{farmId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getFarm(@PathVariable Integer farmId){

        Optional<EFarm> farm = sFarm.getById(farmId);
        if (!farm.isPresent()){
            throw new NotFoundException("farm with specified id not found", "farmId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "farm fetched", new FarmDTO(farm.get())));
    }

    @GetMapping(path = "/farm", produces = "application/json")
    public ResponseEntity<SuccessResponse> getFarmList(){

        List<EFarm> farms = sFarm.getAll();

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "fetched farm list", farms));
    }
}
