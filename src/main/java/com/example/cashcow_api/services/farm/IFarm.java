package com.example.cashcow_api.services.farm;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EFarm;

public interface IFarm {

    Boolean checkExistsByName(String farmName);
    
    EFarm create(FarmDTO farmDTO);

    List<EFarm> getAll();

    Optional<EFarm> getById(Integer farmId);

    EFarm getById(Integer farmId, Boolean handleException);

    Page<EFarm> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EFarm farm);

    EFarm update(Integer farmId, FarmDTO farmDTO);
}
