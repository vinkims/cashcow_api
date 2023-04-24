package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowBreedDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowBreed;

public interface ICowBreed {
    
    Boolean checkExistsByName(String name);

    ECowBreed create(CowBreedDTO cowBreedDTO);

    Optional<ECowBreed> getById(Integer breedId);

    ECowBreed getById(Integer breedId, Boolean handleException);

    Page<ECowBreed> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowBreed cowBreed);

    ECowBreed update(Integer breedId, CowBreedDTO cowBreedDTO);
}
