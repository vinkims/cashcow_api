package com.example.cashcow_api.services.contact;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.contact.ContactTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EContactType;

public interface IContactType {

    EContactType create(ContactTypeDTO contactTypeDTO);
    
    List<EContactType> getAll();
    
    Optional<EContactType> getById(Integer contactTypeId);

    EContactType getById(Integer contactTypeId, Boolean handleException);

    Page<EContactType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EContactType contactType);

    EContactType update(Integer contactTypeId, ContactTypeDTO contactTypeDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
