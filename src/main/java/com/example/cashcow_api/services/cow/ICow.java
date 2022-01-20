package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECow;

import org.springframework.data.domain.Page;

public interface ICow {

    Boolean checkExistsByName(String cowName);
    
    ECow create(CowDTO cowDTO);

    List<ECow> getAll();

    Optional<ECow> getById(Integer cowId);

    List<ECow> getCowsByGender(String gender);

    Page<ECow> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ECow cow);

    ECow update(ECow cow, CowDTO cowDTO);
}
