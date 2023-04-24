package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowWeightDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowWeight;

public interface ICowWeight {
    
    ECowWeight create(CowWeightDTO cowWeightDTO);

    Optional<ECowWeight> getById(Integer cowWeightId);

    ECowWeight getById(Integer cowWeightId, Boolean handleException);

    Page<ECowWeight> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowWeight cowWeight);

    ECowWeight update(Integer cowWeightId, CowWeightDTO cowWeightDTO);
}
