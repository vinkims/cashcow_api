package com.example.cashcow_api.dtos.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.models.EContact;
import com.example.cashcow_api.models.ERole;
import com.example.cashcow_api.models.EUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    
    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDateTime createdOn;

    private ERole role;

    private List<ContactDTO> contacts = new ArrayList<ContactDTO>();

    private ShopDTO shop;

    private Integer userId;

    private Integer roleId;

    private Integer shopId;

    private UserProfileDTO profile;

    public UserDTO(EUser user){
        this.setUserId(user.getId());
        this.setFirstName(user.getFirstName());
        this.setMiddleName(user.getMiddleName());
        this.setLastName(user.getLastName());
        this.setCreatedOn(user.getCreatedOn());
        this.setRole(user.getRole());
        setContactData(user.getContacts());
        if (user.getShop() != null){
            this.setShop(new ShopDTO(user.getShop()));
        }
    }

    public void setContactData(List<EContact> contactList){
        if (contactList == null || contactList.isEmpty()){ return; }
        for (EContact contact : contactList){
            contacts.add(new ContactDTO(contact));
        }
    }
}
