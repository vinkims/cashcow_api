package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowFeedingDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowFeeding;

public interface ICowFeeding {
    
    ECowFeeding create(CowFeedingDTO cowFeedingDTO);

    Optional<ECowFeeding> getById(Integer cowFeedingId);

    ECowFeeding getById(Integer cowFeedingId, Boolean handleException);

    Page<ECowFeeding> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowFeeding cowFeeding);

    ECowFeeding update(Integer cowFeedingId, CowFeedingDTO cowFeedingDTO);
}
