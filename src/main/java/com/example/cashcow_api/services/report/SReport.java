package com.example.cashcow_api.services.report;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.general.DateParamDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.dtos.report.ReportDTO;
import com.example.cashcow_api.services.milk.IMilkProduction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SReport implements IReport {

    @Autowired
    private IMilkProduction sMilkProduction;

    

    @Override
    public List<MilkProductionSummaryDTO> getCurrentWeekProduction(){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return sMilkProduction.getMilkProductionSummary(startDate, today);
    }

    @Override
    public List<MilkProductionSummaryDTO> getPreviousWeekProduction(){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.with(DayOfWeek.MONDAY).minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = today.with(DayOfWeek.MONDAY).minusDays(1).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return sMilkProduction.getMilkProductionSummary(startDate, endDate);
    }

    @Override
    public List<MilkProductionSummaryDTO> getProductionByDate(DateParamDTO dateParamDTO){
        return sMilkProduction.getMilkProductionSummary(dateParamDTO.getStartDate(), dateParamDTO.getEndDate());
    }

    @Override
    public ReportDTO getReportByDate(DateParamDTO dateParamDTO) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCurrentWeekSummary(getCurrentWeekProduction());
        reportDTO.setPreviousWeekSummary(getPreviousWeekProduction());
        reportDTO.setProductionSummary(getProductionByDate(dateParamDTO));
        return reportDTO;
    }
    
}
