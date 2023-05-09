package com.example.cashcow_api.controllers;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;

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

import com.example.cashcow_api.dtos.transaction.PaymentChannelDTO;
import com.example.cashcow_api.models.EPaymentChannel;
import com.example.cashcow_api.services.transaction.IPaymentChannel;

@RestController
@RequestMapping(path = "/payment/channel")
public class CPaymentChannel {
    
    @Autowired
    private IPaymentChannel sPaymentChannel;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createPaymentChannel(@RequestBody PaymentChannelDTO paymentChannelDTO) throws URISyntaxException {

        EPaymentChannel paymentChannel = sPaymentChannel.create(paymentChannelDTO);

        return ResponseEntity
            .created(new URI("/" + paymentChannel.getId()))
            .body(new SuccessResponse(201, "successfully created payment channel", new PaymentChannelDTO(paymentChannel)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        List<String> allowedFields = new ArrayList<>();

        Page<EPaymentChannel> paymentChannels = sPaymentChannel.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched payment channels", paymentChannels, 
                PaymentChannelDTO.class, EPaymentChannel.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getPaymentChannel(@PathVariable Integer id) {

        EPaymentChannel paymentChannel = sPaymentChannel.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched payment channel", new PaymentChannelDTO(paymentChannel)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updatePaymentChannel(@PathVariable Integer id, @RequestBody PaymentChannelDTO paymentChannelDTO) {

        EPaymentChannel paymentChannel = sPaymentChannel.update(id, paymentChannelDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated payment channel", new PaymentChannelDTO(paymentChannel)));
    }
}
