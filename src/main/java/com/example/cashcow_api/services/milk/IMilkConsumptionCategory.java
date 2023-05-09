package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionCategoryDTO;
import com.example.cashcow_api.models.EMilkConsumptionCategory;

public interface IMilkConsumptionCategory {
    
    EMilkConsumptionCategory create(MilkConsumptionCategoryDTO categoryDTO);

    List<EMilkConsumptionCategory> getAll();

    Optional<EMilkConsumptionCategory> getById(Integer categoryId);

    EMilkConsumptionCategory getById(Integer categoryId, Boolean handleException);

    Page<EMilkConsumptionCategory> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EMilkConsumptionCategory category);

    EMilkConsumptionCategory update(Integer categoryId, MilkConsumptionCategoryDTO categoryDTO);
}
