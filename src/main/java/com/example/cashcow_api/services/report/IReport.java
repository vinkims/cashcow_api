package com.example.cashcow_api.services.report;

import java.util.List;

import com.example.cashcow_api.dtos.general.DateParamDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.dtos.report.ReportDTO;

public interface IReport {

    List<MilkProductionSummaryDTO> getCurrentWeekProduction();

    List<MilkProductionSummaryDTO> getPreviousWeekProduction();

    List<MilkProductionSummaryDTO> getProductionByDate(DateParamDTO dateParamDTO);

    ReportDTO getReportByDate(DateParamDTO dateParamDTO);

}
