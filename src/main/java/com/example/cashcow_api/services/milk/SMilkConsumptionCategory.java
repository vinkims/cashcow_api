package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.milk.MilkConsumptionCategoryDTO;
import com.example.cashcow_api.models.EMilkConsumptionCategory;
import com.example.cashcow_api.repositories.MilkConsumptionCategoryDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMilkConsumptionCategory implements IMilkConsumptionCategory {

    @Autowired
    private MilkConsumptionCategoryDAO categoryDAO;

    @Override
    public EMilkConsumptionCategory create(MilkConsumptionCategoryDTO categoryDTO) {
        EMilkConsumptionCategory category = new EMilkConsumptionCategory();
        if (categoryDTO.getDescription() != null){
            category.setDescription(categoryDTO.getDescription());
        }
        category.setName(categoryDTO.getName());
        save(category);

        return category;
    }

    @Override
    public List<EMilkConsumptionCategory> getAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Optional<EMilkConsumptionCategory> getById(Integer categoryId) {
        return categoryDAO.findById(categoryId);
    }

    @Override
    public void save(EMilkConsumptionCategory category) {
        categoryDAO.save(category);
    }
    
}
