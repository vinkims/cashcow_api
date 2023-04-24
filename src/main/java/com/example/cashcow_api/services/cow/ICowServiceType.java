package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowServiceTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowServiceType;

public interface ICowServiceType {
    
    Boolean checkExistsByName(String name);

    ECowServiceType create(CowServiceTypeDTO serviceTypeDTO);

    List<ECowServiceType> getAll();

    Optional<ECowServiceType> getById(Integer serviceTypeId);

    ECowServiceType getById(Integer serviceTypeId, Boolean handleException);

    Page<ECowServiceType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowServiceType serviceType);

    ECowServiceType update(Integer serviceTypeId, CowServiceTypeDTO serviceTypeDTO);
}
