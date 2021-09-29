package com.example.cashcow_api.dtos.general;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.example.cashcow_api.exceptions.InvalidInputException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data
public class DateParamDTO {
    
    Logger logger = LoggerFactory.getLogger(DateParamDTO.class);

    private LocalDateTime endDate;

    private LocalDateTime startDate;

    public DateParamDTO(Map<String, String> dateParams) throws IllegalArgumentException, 
            IllegalAccessException, NoSuchFieldException, SecurityException{

        String endDateStr = dateParams.getOrDefault("endDate", null);

        String startDateStr = dateParams.getOrDefault("startDate", null);

        if (endDateStr != null && startDateStr != null){

            setDateField(startDateStr, this.getClass().getDeclaredField("startDate"));
            setDateField(endDateStr, this.getClass().getDeclaredField("endDate"));

            if (startDate.isAfter(endDate)){
                throw new InvalidInputException("invalid date range provided; startDate should be less than endDate", "startDate/endDate");
            }
        } else {
            setEndDate(LocalDateTime.now());
            setStartDate(LocalDate.now().atTime(LocalTime.MIDNIGHT));
        }
    }

    public void setDateField(String dateValue, Field field) throws IllegalArgumentException, IllegalAccessException{

        try {
            Object fieldValue = dateValue == null ? LocalDateTime.now() : stringToDate(dateValue);
            field.set(this, fieldValue);
        } catch (ParseException e) {
            field.set(this, LocalDateTime.now());
            logger.error("\n[MSG] - {}\n[CAUSE] - {}", e.getMessage(), e.getCause());
        }
    }

    /**
     * Converts string to date obj
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public LocalDateTime stringToDate(String dateStr) throws ParseException{
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm"));
    }
}
