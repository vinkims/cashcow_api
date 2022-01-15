package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.milk.MilkConsumptionCategoryDTO;
import com.example.cashcow_api.models.EMilkConsumptionCategory;

public interface IMilkConsumptionCategory {
    
    EMilkConsumptionCategory create(MilkConsumptionCategoryDTO categoryDTO);

    List<EMilkConsumptionCategory> getAll();

    Optional<EMilkConsumptionCategory> getById(Integer categoryId);

    void save(EMilkConsumptionCategory category);
}
