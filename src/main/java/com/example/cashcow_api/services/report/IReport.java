package com.example.cashcow_api.services.report;

import java.util.List;

import com.example.cashcow_api.dtos.general.DateParamDTO;
import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO;
import com.example.cashcow_api.dtos.report.ReportDTO;

public interface IReport {

    List<MilkProductionSummaryDTO> getCurrentWeekProduction();

    List<DailyCowProductionDTO> getDailyCowProduction(Integer cowId);

    List<MilkSaleSummaryDTO> getMilkSaleSummary(DateParamDTO dateParamDTO, Integer shopId);

    List<MilkSaleTotalDTO> getMilkSaleTotal(DateParamDTO dateParamDTO);

    List<MilkProductionSummaryDTO> getPreviousWeekProduction();

    List<MilkProductionSummaryDTO> getProductionByDate(DateParamDTO dateParamDTO);

    ReportDTO getMilkSaleReport(DateParamDTO dateParamDTO, Integer shopId);

    ReportDTO getReportByDateAndCow(DateParamDTO dateParamDTO, Integer cowId);

}
