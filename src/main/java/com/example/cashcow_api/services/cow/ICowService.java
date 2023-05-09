package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowServiceDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowService;

import org.springframework.data.domain.Page;

public interface ICowService {
    
    ECowService create(CowServiceDTO cowServiceDTO);

    Optional<ECowService> getById(Integer cowServiceId);

    ECowService getById(Integer cowServiceId, Boolean handleException);

    Page<ECowService> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ECowService cowService);

    ECowService update(Integer cowServiceId, CowServiceDTO cowServiceDTO);
}
