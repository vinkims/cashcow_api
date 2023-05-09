package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowDeathDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowDeath;

public interface ICowDeath {
    
    ECowDeath create(CowDeathDTO cowDeathDTO);

    Optional<ECowDeath> getById(Integer deathId);

    ECowDeath getById(Integer deathId, Boolean handleException);

    Page<ECowDeath> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowDeath cowDeath);

    ECowDeath update(Integer deathId, CowDeathDTO cowDeathDTO);
}
