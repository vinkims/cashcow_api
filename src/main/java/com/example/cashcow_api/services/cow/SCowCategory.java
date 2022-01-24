package com.example.cashcow_api.services.cow;

import java.util.Optional;

import com.example.cashcow_api.models.ECowCategory;
import com.example.cashcow_api.repositories.CowCategoryDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCowCategory implements ICowCategory{

    @Autowired private CowCategoryDAO categoryDAO;
    
    @Override
    public Optional<ECowCategory> getById(Integer categoryId) {
        return categoryDAO.findById(categoryId);
    }
    
}
