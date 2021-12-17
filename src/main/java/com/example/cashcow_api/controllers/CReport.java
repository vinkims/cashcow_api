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

    @GetMapping(path = "/report/sale", produces = "application/json")
    public ResponseEntity<SuccessResponse> getMilkSaleSummary( @RequestParam Map<String, String> params) 
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{

        DateParamDTO dateParamDTO = new DateParamDTO(params);
        Integer shopId;
        try {
            shopId = Integer.valueOf(params.get("shop.id"));
        } catch(NumberFormatException e){
            shopId = null;
        }

        ReportDTO saleReport = sReport.getMilkSaleReport(dateParamDTO, shopId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "returned sale report", saleReport));
    }

    @GetMapping(path = "/report", produces = "application/json")
    public ResponseEntity<SuccessResponse> getMilkProductionSummary(@RequestParam Map<String, String> params) 
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{

        DateParamDTO dateParamDTO = new DateParamDTO(params);
        Integer cowId;
        try{
            cowId = Integer.valueOf(params.get("cow.id"));
        } catch(NumberFormatException e){
            cowId = null;
        }
        
        ReportDTO reports = sReport.getReportByDateAndCow(dateParamDTO, cowId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "returned production list", reports));
    }

    @GetMapping(path = "/report/user", produces = "application/json")
    public ResponseEntity<SuccessResponse> getUsersSummary(){

        ReportDTO usersReport = sReport.getUsersSummaryReport();

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "returned users summary", usersReport));
    }
}
