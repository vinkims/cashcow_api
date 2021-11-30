package com.example.cashcow_api.dtos.report;

import java.util.List;

import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ReportDTO {

    private List<MilkProductionSummaryDTO> currentWeekSummary;
    
    private List<MilkProductionSummaryDTO> previousWeekSummary;

    private List<MilkProductionSummaryDTO> productionSummary;
}
