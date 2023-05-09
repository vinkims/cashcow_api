package com.example.cashcow_api.services.breeding;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.breeding.BreedingTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EBreedingType;

public interface IBreedingType {
    
    EBreedingType create(BreedingTypeDTO breedingTypeDTO);

    Optional<EBreedingType> getById(Integer typeId);

    EBreedingType getById(Integer typeId, Boolean handleException);

    Page<EBreedingType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EBreedingType breedingType);

    EBreedingType update(Integer typeId, BreedingTypeDTO breedingTypeDTO);
}
