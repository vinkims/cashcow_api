package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowServiceTypeDTO;
import com.example.cashcow_api.models.ECowServiceType;

public interface ICowServiceType {
    
    Boolean checkExistsByName(String name);

    ECowServiceType create(CowServiceTypeDTO serviceTypeDTO);

    List<ECowServiceType> getAll();

    Optional<ECowServiceType> getById(Integer serviceTypeId);

    void save(ECowServiceType serviceType);
}
