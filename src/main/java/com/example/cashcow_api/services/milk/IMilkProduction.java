package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.models.EMilkProduction;

import org.springframework.data.domain.Page;

public interface IMilkProduction {
    
    EMilkProduction create(MilkProductionDTO productionDTO);

    Optional<EMilkProduction> getById(Integer productionId);

    List<DailyCowProductionDTO> getDailyCowProduction(LocalDateTime startDate, LocalDateTime endDate, Integer cowId);

    List<MilkProductionSummaryDTO> getMilkProductionSummary(LocalDateTime startDate, LocalDateTime endDate);

    Page<EMilkProduction> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EMilkProduction production);
}
