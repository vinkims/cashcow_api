package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.farm.IFarm;

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
@RequestMapping(path = "/farm")
public class CFarm {
    
    @Autowired
    private IFarm sFarm;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createFarm(@Valid @RequestBody FarmDTO farmDTO) 
            throws URISyntaxException {

        EFarm farm = sFarm.create(farmDTO);

        return ResponseEntity
            .created(new URI("/" + farm.getId()))
            .body(new SuccessResponse(201, "farm successfully created", new FarmDTO(farm)));
    }

    @GetMapping(path = "/{farmId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getFarm(@PathVariable Integer farmId) {

        EFarm farm = sFarm.getById(farmId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "farm fetched", new FarmDTO(farm)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getFarmList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "updatedOn", "status.id"));

        Page<EFarm> farms = sFarm.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched farm list", farms, 
                FarmDTO.class, EFarm.class));
    }

    @PatchMapping(path ="/{farmId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateFarm(@PathVariable Integer farmId, @RequestBody FarmDTO farmDTO) {
        
        EFarm farm = sFarm.update(farmId, farmDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated farm", new FarmDTO(farm)));
    }
}
