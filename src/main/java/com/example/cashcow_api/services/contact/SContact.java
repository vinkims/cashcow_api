package com.example.cashcow_api.services.contact;

import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.models.EContact;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.ContactDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SContact {
    
    @Autowired private SContactType sContactType;

    @Autowired private ContactDAO contactDAO;

    public EContact create(EUser user, ContactDTO contactDTO){
        EContact contact = new EContact();
        contact.setContactType(sContactType.getById(contactDTO.getContactTypeId()).get());
        contact.setUser(user);
        contact.setValue(contactDTO.getContactValue());
        return contact;
    }

    public Boolean checkExistsByValue(String contactValue){
        return contactDAO.existsByValue(contactValue);
    }

    public void save(EContact contact){
        contactDAO.save(contact);
    }
}
