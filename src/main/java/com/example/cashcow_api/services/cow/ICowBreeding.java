package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowBreedingDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowBreeding;

public interface ICowBreeding {
    
    ECowBreeding create(CowBreedingDTO cowBreedingDTO);

    Optional<ECowBreeding> getById(Integer breedingId);

    ECowBreeding getById(Integer breedingId, Boolean handleException);

    Page<ECowBreeding> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowBreeding cowBreeding);

    ECowBreeding update(Integer breedingId, CowBreedingDTO cowBreedingDTO);
}
