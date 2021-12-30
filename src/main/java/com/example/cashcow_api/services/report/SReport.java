package com.example.cashcow_api.services.report;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.cashcow_api.dtos.general.DateParamDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleTotalDTO;
import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO;
import com.example.cashcow_api.dtos.report.ReportDTO;
import com.example.cashcow_api.dtos.user.SummaryUserDTO;
import com.example.cashcow_api.services.milk.IMilkProduction;
import com.example.cashcow_api.services.milk.IMilkSale;
import com.example.cashcow_api.services.user.IUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SReport implements IReport {

    @Autowired
    private IMilkProduction sMilkProduction;

    @Autowired
    private IMilkSale sMilkSale;

    @Autowired
    private IUser sUser;

    @Override
    public List<MilkSaleSummaryDTO> getCurrentWeekShopSale(Integer shopId){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return sMilkSale.getMilkSaleSummaryByShop(startDate, today, shopId);
    }

    @Override
    public List<MilkProductionSummaryDTO> getCurrentWeekProduction(){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return sMilkProduction.getMilkProductionSummary(startDate, today);
    }

    public Map<LocalDate, Double> getCurrentWeek(){
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<MilkProductionSummaryDTO> currentList = sMilkProduction.getMilkProductionSummary(startDate, endDate);
        return currentList.stream()
            .collect(Collectors.toMap(MilkProductionSummaryDTO::getCreatedOn, MilkProductionSummaryDTO::getQuantity));
    }

    @Override
    public List<CustomerSaleSummaryDTO> getCustomerSaleSummary(DateParamDTO dateParamDTO, Integer customerId){
        return sMilkSale.getCustomerSaleSummary(dateParamDTO.getStartDate(), dateParamDTO.getEndDate(), customerId);
    }

    @Override
    public List<CustomerSaleTotalDTO> getCustomerSaleTotal(DateParamDTO dateParamDTO, Integer customerId){
        return sMilkSale.getCustomerSaleTotal(dateParamDTO.getStartDate(), dateParamDTO.getEndDate(), customerId);
    }

    @Override
    public List<DailyCowProductionDTO> getDailyCowProduction(Integer cowId){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = today.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return sMilkProduction.getDailyCowProduction(startDate, endDate, cowId);
    }

    @Override
    public List<MilkSaleSummaryDTO> getMilkSaleSummary(DateParamDTO dateParamDTO, Integer shopId){
        List<MilkSaleSummaryDTO> saleSummary = shopId == null 
            ? sMilkSale.getMilkSaleSummary(dateParamDTO.getStartDate(), dateParamDTO.getEndDate())
            : sMilkSale.getMilkSaleSummaryByShop(dateParamDTO.getStartDate(), dateParamDTO.getEndDate(), shopId);
        return saleSummary;
    }

    @Override
    public List<MilkSaleTotalDTO> getMilkSaleTotal(DateParamDTO dateParamDTO){
        return sMilkSale.getMilkSaleTotal(dateParamDTO.getStartDate(), dateParamDTO.getEndDate());
    }
    
    @Override
    public List<MilkProductionSummaryDTO> getPreviousWeekProduction(){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.with(DayOfWeek.MONDAY).minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = today.with(DayOfWeek.MONDAY).minusDays(1).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return sMilkProduction.getMilkProductionSummary(startDate, endDate);
    }

    @Override
    public List<MilkSaleSummaryDTO> getPreviousWeekShopSale(Integer shopId){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.with(DayOfWeek.MONDAY).minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = today.with(DayOfWeek.MONDAY).minusDays(1).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return sMilkSale.getMilkSaleSummaryByShop(startDate, endDate, shopId);
    }

    @Override
    public List<MilkProductionSummaryDTO> getProductionByDate(DateParamDTO dateParamDTO){
        return sMilkProduction.getMilkProductionSummary(dateParamDTO.getStartDate(), dateParamDTO.getEndDate());
    }

    @Override
    public List<MilkProductionSummaryDTO> getProductionByDateAndCow(DateParamDTO dateParamDTO, Integer cowId){
        return sMilkProduction.getProductionSummaryByCow(dateParamDTO.getStartDate(), dateParamDTO.getEndDate(), cowId);
    }

    @Override
    public List<SummaryUserDTO> getUsersReport(){
        return sUser.getUserCountPerRole();
    }

    /*@Override
    public List<EWeight> getWeightSummary(DateParamDTO dateParamDTO, Integer cowId){
        return sWeight.getWeightSummary(dateParamDTO.getStartDate(), dateParamDTO.getEndDate(), cowId);
    }*/

    @Override
    public ReportDTO getCustomerReport(DateParamDTO dateParamDTO, Integer customerId){
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCustomerSaleSummary(getCustomerSaleSummary(dateParamDTO, customerId));
        reportDTO.setCustomerSaleTotal(getCustomerSaleTotal(dateParamDTO, customerId));
        return reportDTO;
    }

    @Override
    public ReportDTO getMilkSaleReport(DateParamDTO dateParamDTO, Integer shopId){
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCurrentWeekMilkSale(getCurrentWeekShopSale(shopId));
        reportDTO.setMilkSaleSummary(getMilkSaleSummary(dateParamDTO, shopId));
        reportDTO.setMilkSaleTotal(getMilkSaleTotal(dateParamDTO));
        reportDTO.setPreviousWeekMilkSale(getPreviousWeekShopSale(shopId));
        return reportDTO;
    }

    @Override
    public ReportDTO getReportByDateAndCow(DateParamDTO dateParamDTO, Integer cowId) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCowProductionSummary(getProductionByDateAndCow(dateParamDTO, cowId));
        reportDTO.setCurrentWeek(getCurrentWeek());
        reportDTO.setCurrentWeekSummary(getCurrentWeekProduction());
        reportDTO.setDailyCowProduction(getDailyCowProduction(cowId));
        reportDTO.setPreviousWeekSummary(getPreviousWeekProduction());
        reportDTO.setProductionSummary(getProductionByDate(dateParamDTO));
        return reportDTO;
    }

    @Override
    public ReportDTO getUsersSummaryReport(){
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setUserSummary(getUsersReport());
        return reportDTO;
    }
    
}
