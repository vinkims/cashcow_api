package com.example.cashcow_api.services.cow;

import java.util.Optional;

import com.example.cashcow_api.models.ECowCategory;

public interface ICowCategory {
    
    Optional<ECowCategory> getById(Integer categoryId);
}
