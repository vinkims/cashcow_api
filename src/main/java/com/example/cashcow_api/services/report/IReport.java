package com.example.cashcow_api.services.report;

import java.util.List;

import com.example.cashcow_api.dtos.general.DateParamDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleTotalDTO;
import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTypeDTO;
import com.example.cashcow_api.dtos.report.ReportDTO;
import com.example.cashcow_api.dtos.transaction.TransactionSummaryDTO;
import com.example.cashcow_api.dtos.user.SummaryUserDTO;
import com.example.cashcow_api.models.ECowWeight;

public interface IReport {

    List<MilkSaleSummaryDTO> getCurrentWeekShopSale(Integer shopId);

    List<MilkProductionSummaryDTO> getCurrentWeekProduction();

    List<CustomerSaleSummaryDTO> getCustomerSaleSummary(DateParamDTO dateParamDTO, Integer customerId);

    List<CustomerSaleTotalDTO> getCustomerSaleTotal(DateParamDTO dateParamDTO, Integer customerId);

    List<DailyCowProductionDTO> getDailyCowProduction(Integer cowId);

    List<MilkConsumptionSummaryDTO> getMilkConsumptionSummary(DateParamDTO dateParamDTO);

    List<MilkSaleSummaryDTO> getMilkSaleSummary(DateParamDTO dateParamDTO, Integer shopId);

    List<MilkSaleTypeDTO> getMilkSaleTypeSummary(DateParamDTO dateParamDTO, Integer shopId);

    List<MilkSaleTotalDTO> getMilkSaleTotal(DateParamDTO dateParamDTO);

    List<MilkProductionSummaryDTO> getPreviousWeekProduction();

    List<MilkSaleSummaryDTO> getPreviousWeekShopSale(Integer shopId);

    List<MilkProductionSummaryDTO> getProductionByDate(DateParamDTO dateParamDTO);

    List<MilkProductionSummaryDTO> getProductionByDateAndCow(DateParamDTO dateParamDTO, Integer cowId);

    List<TransactionSummaryDTO> getTransactionSummary(DateParamDTO dateParamDTO);

    //List<EWeight> getWeightSummary(DateParamDTO dateParamDTO, Integer cowId);

    List<SummaryUserDTO> getUsersReport();

    ReportDTO getCustomerReport(DateParamDTO dateParamDTO, Integer customerId);

    ReportDTO getMilkConsumptionReport(DateParamDTO dateParamDTO);

    ReportDTO getMilkSaleReport(DateParamDTO dateParamDTO, Integer shopId);

    ReportDTO getReportByDateAndCow(DateParamDTO dateParamDTO, Integer cowId);

    ReportDTO getUsersSummaryReport(DateParamDTO dateParamDTO, Integer userId);

    ReportDTO getTransactionReport(DateParamDTO dateParamDTO);

}
