package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.BreedDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EBreed;

public interface IBreed {
    
    Boolean checkExistsByName(String name);

    EBreed create(BreedDTO cowBreedDTO);

    Optional<EBreed> getById(Integer breedId);

    EBreed getById(Integer breedId, Boolean handleException);

    Page<EBreed> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EBreed cowBreed);

    EBreed update(Integer breedId, BreedDTO cowBreedDTO);
}
