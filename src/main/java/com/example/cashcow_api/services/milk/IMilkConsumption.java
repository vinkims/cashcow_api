package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionSummaryDTO;
import com.example.cashcow_api.models.EMilkConsumption;

import org.springframework.data.domain.Page;

public interface IMilkConsumption {
    
    EMilkConsumption create(MilkConsumptionDTO consumptionDTO);

    Optional<EMilkConsumption> getById(Integer id);

    EMilkConsumption getById(Integer id, Boolean handleException);

    List<MilkConsumptionSummaryDTO> getMilkConsumptionSummaryByDate(LocalDateTime startDate, LocalDateTime endDate);

    Page<EMilkConsumption> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EMilkConsumption consumption);

    EMilkConsumption update(Integer id, MilkConsumptionDTO consumptionDTO);
}
