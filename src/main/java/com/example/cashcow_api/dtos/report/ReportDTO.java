package com.example.cashcow_api.dtos.report;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO;
import com.example.cashcow_api.dtos.user.SummaryUserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ReportDTO {

    private Map<LocalDate, Double> currentWeek;
    
    private List<MilkSaleSummaryDTO> currentWeekMilkSale;
    
    private List<MilkProductionSummaryDTO> currentWeekSummary;

    private List<DailyCowProductionDTO> dailyCowProduction;

    private List<MilkSaleSummaryDTO> milkSaleSummary;

    private List<MilkSaleTotalDTO> milkSaleTotal;
    
    private List<MilkProductionSummaryDTO> previousWeekSummary;

    private List<MilkProductionSummaryDTO> productionSummary;

    private List<SummaryUserDTO> userSummary;
}