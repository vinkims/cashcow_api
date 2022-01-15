package com.example.cashcow_api.dtos.report;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.cashcow_api.dtos.milk.CustomerSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleTotalDTO;
import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO;
import com.example.cashcow_api.dtos.transaction.EmployeeTransactionDTO;
import com.example.cashcow_api.dtos.transaction.TransactionSummaryDTO;
import com.example.cashcow_api.dtos.user.SummaryUserDTO;
import com.example.cashcow_api.models.EWeight;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ReportDTO {

    private List<MilkProductionSummaryDTO> cowProductionSummary;
    
    private Map<LocalDate, Double> currentWeek;
    
    private List<MilkSaleSummaryDTO> currentWeekMilkSale;
    
    private List<MilkProductionSummaryDTO> currentWeekSummary;

    private List<CustomerSaleSummaryDTO> customerSaleSummary;

    private List<CustomerSaleTotalDTO> customerSaleTotal;

    private List<DailyCowProductionDTO> dailyCowProduction;

    private List<EmployeeTransactionDTO> employeeExpenses;

    private List<MilkConsumptionSummaryDTO> milkConsumptionSummary;

    private List<MilkSaleSummaryDTO> milkSaleSummary;

    private List<MilkSaleTotalDTO> milkSaleTotal;

    private List<MilkSaleSummaryDTO> previousWeekMilkSale;
    
    private List<MilkProductionSummaryDTO> previousWeekSummary;

    private List<MilkProductionSummaryDTO> productionSummary;

    private List<TransactionSummaryDTO> transactionSummary;

    private List<SummaryUserDTO> userSummary;

    private List<EWeight> weightSummary;
}
