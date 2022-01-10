package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowImageDTO;
import com.example.cashcow_api.models.ECowImage;

public interface ICowImage {
    
    ECowImage create(CowImageDTO cowImageDTO);

    Optional<ECowImage> getById(Integer id);

    List<ECowImage> getList();

    void save(ECowImage cowImage);
}
