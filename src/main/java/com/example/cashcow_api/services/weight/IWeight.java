package com.example.cashcow_api.services.weight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.weight.WeightDTO;
import com.example.cashcow_api.dtos.weight.WeightSummaryDTO;
import com.example.cashcow_api.models.EWeight;

import org.springframework.data.domain.Page;

public interface IWeight {
 
    EWeight create(WeightDTO weightDTO);

    Optional<EWeight> getById(Integer id);

    Page<EWeight> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EWeight weight);
}
