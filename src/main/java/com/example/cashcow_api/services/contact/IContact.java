package com.example.cashcow_api.services.contact;
import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.models.EContact;
import com.example.cashcow_api.models.EUser;
import java.util.Optional;

public interface IContact {
    
    EContact create(EUser user, ContactDTO contactDTO);

    Boolean checkExistsByValue(String contactValue);

    void deleteContact(EContact contact);

    Optional<EContact> getByUserAndContactType(Integer userId, Integer contactTypeId);

    EContact getByValue(String contactValue);

    void save(EContact contact);

    void updateContact(String contactValue, Integer userId, Integer contactTypeId);
}
