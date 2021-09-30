package com.example.cashcow_api.runners;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.dtos.user.UserDTO;
import com.example.cashcow_api.dtos.user.UserProfileDTO;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.services.user.IUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdminRunner implements CommandLineRunner{

    @Value(value = "${default.value.user.admin-email}")
    private String email;

    @Value(value = "${default.value.user.admin-password}")
    private String password;

    @Value(value = "${default.value.contact.email-type-id}")
    private Integer emailTypeId;

    @Value(value = "${default.value.role.system-admin-role-id}")
    private Integer systemAdminRoleId;

    @Autowired private IUser sUser;

    @Override
    public void run(String... args) throws Exception {
        
        String firstName = "system";
        String lastName = "admin";

        log.info(">>> check if admin client exists");
        Optional<EUser> user = sUser.getByContactValue(email);
        if (user.isPresent()){
            return;
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setPasscode(password);

        List<ContactDTO> contacts = new ArrayList<>();
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactTypeId(emailTypeId);
        contactDTO.setContactValue(email);
        contacts.add(contactDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setContacts(contacts);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setProfile(userProfileDTO);
        userDTO.setRoleId(systemAdminRoleId);

        EUser admin = sUser.create(userDTO);

        log.info(
            "SysAdmin created: id=> {}, name=>{}, email=>{}",
            admin.getId(),
            String.format("%s %s", firstName, lastName),
            email
        );
    }
    
}
