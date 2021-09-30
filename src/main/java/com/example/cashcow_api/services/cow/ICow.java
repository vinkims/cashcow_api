package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.models.ECow;

public interface ICow {
    
    ECow create(CowDTO cowDTO);

    List<ECow> getAll();

    Optional<ECow> getById(Integer cowId);

    void save(ECow cow);
}
