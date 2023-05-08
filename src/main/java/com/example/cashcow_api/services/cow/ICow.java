package com.example.cashcow_api.services.cow;

import java.lang.reflect.InvocationTargetException;
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

    ECow getById(Integer cowId, Boolean handleException);

    Page<ECow> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ECow cow);

    ECow update(Integer cowId, CowDTO cowDTO) throws IllegalAccessException, IllegalArgumentException, 
        InvocationTargetException, NoSuchMethodException, SecurityException;
}
