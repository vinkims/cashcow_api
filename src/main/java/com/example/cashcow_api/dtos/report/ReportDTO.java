package com.example.cashcow_api.dtos.report;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ReportDTO {

    private Map<LocalDate, Double> currentWeek;
    
    private List<MilkProductionSummaryDTO> currentWeekSummary;

    private List<DailyCowProductionDTO> dailyCowProduction;
    
    private List<MilkProductionSummaryDTO> previousWeekSummary;

    private List<MilkProductionSummaryDTO> productionSummary;
}
