package com.example.cashcow_api.services.contact;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.EContactType;
import com.example.cashcow_api.repositories.ContactTypeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SContactType {
    
    @Autowired private ContactTypeDAO contactTypeDAO;

    /**
     * Get contact type by id
     * @param contactTypeId
     * @return
     */
    public Optional<EContactType> getById(Integer contactTypeId){
        return contactTypeDAO.findById(contactTypeId);
    }

    public List<EContactType> getAll(){
        return contactTypeDAO.findAll();
    }

    public void save(EContactType contactType){
        contactTypeDAO.save(contactType);
    }
}
