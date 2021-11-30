package com.example.cashcow_api.controllers;

import java.util.Map;

import com.example.cashcow_api.dtos.general.DateParamDTO;
import com.example.cashcow_api.dtos.report.ReportDTO;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.report.IReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CReport {
    
    @Autowired
    private IReport sReport;

    @GetMapping(path = "/report", produces = "application/json")
    public ResponseEntity<SuccessResponse> getMilkProductionSummary(@RequestParam Map<String, String> params) 
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{

        DateParamDTO dateParamDTO = new DateParamDTO(params);
        
        ReportDTO reports = sReport.getReportByDate(dateParamDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "returned production list", reports));
    }
}
