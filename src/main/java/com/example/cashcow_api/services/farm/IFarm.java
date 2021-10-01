package com.example.cashcow_api.services.farm;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.models.EFarm;

public interface IFarm {

    Boolean checkExistsByName(String farmName);
    
    EFarm create(FarmDTO farmDTO);

    List<EFarm> getAll();

    Optional<EFarm> getById(Integer farmId);

    void save(EFarm farm);
}
