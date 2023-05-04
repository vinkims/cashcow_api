package com.example.cashcow_api.services.contact;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.models.EContact;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.ContactDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SContact implements IContact {
    
    @Autowired 
    private IContactType sContactType;

    @Autowired 
    private ContactDAO contactDAO;

    @Override
    public EContact create(EUser user, ContactDTO contactDTO){
        EContact contact = new EContact();
        contact.setContactType(sContactType.getById(contactDTO.getContactTypeId()).get());
        contact.setCreatedOn(LocalDateTime.now());
        contact.setUser(user);
        contact.setValue(contactDTO.getContactValue());
        return contact;
    }

    @Override
    public Boolean checkExistsByValue(String contactValue){
        return contactDAO.existsByValue(contactValue);
    }

    @Override
    public void deleteContact(EContact contact){
        contactDAO.delete(contact);
    }

    @Override
    public Optional<EContact> getByUserAndContactType(Integer userId, Integer contactTypeId) {
        return contactDAO.findByUserIdAndContactTypeId(userId, contactTypeId);
    }

    @Override
    public EContact getByValue(String contactValue) {
        return contactDAO.findByValue(contactValue);
    }

    @Override
    public void save(EContact contact){
        contactDAO.save(contact);
    }

    @Override
    public void updateContact(String contactValue, Integer userId, Integer contactTypeId) {
        contactDAO.updateContact(contactValue, userId, contactTypeId);
    }
}
