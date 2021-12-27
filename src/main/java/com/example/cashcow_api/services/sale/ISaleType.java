package com.example.cashcow_api.services.sale;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.ESaleType;

public interface ISaleType {
    
    Optional<ESaleType> getById(Integer id);

    List<ESaleType> getList();
}
