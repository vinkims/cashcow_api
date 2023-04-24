package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowCategoryDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowCategory;

public interface ICowCategory {

    ECowCategory create(CowCategoryDTO categoryDTO);
    
    Optional<ECowCategory> getById(Integer categoryId);

    ECowCategory getById(Integer catgoryId, Boolean handleException);

    Page<ECowCategory> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowCategory category);

    ECowCategory update(Integer categoryId, CowCategoryDTO categoryDTO);
}
