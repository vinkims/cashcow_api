package com.example.cashcow_api.dtos.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.dtos.farm.FarmDTO;
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

    private Float balance;
    
    private List<@Valid ContactDTO> contacts = new ArrayList<ContactDTO>();

    private LocalDateTime createdOn;

    private FarmDTO farm;

    private Integer farmId;

    private String firstName;

    private String lastName;

    private String middleName;

    private @Valid UserProfileDTO profile;

    private ERole role;

    private Integer roleId;

    private ShopDTO shop;

    private Integer shopId;

    private String status;

    private Integer statusId;

    private Integer userId;

    public UserDTO(EUser user){
        setBalance(user.getBalance());
        setContactData(user.getContacts());
        this.setCreatedOn(user.getCreatedOn());
        if (user.getFarm() != null){
            this.setFarm(new FarmDTO(user.getFarm()));
        }
        this.setFirstName(user.getFirstName());
        if (user.getLastName() != null){
            this.setLastName(user.getLastName());
        }
        if (user.getMiddleName() != null){
            this.setMiddleName(user.getMiddleName());
        }
        this.setProfile(new UserProfileDTO(user.getProfile()));
        this.setRole(user.getRole());
        if (user.getShop() != null){
            this.setShop(new ShopDTO(user.getShop()));
        }
        setStatus(user.getStatus().getName());
        this.setUserId(user.getId());
    }

    public void setContactData(List<EContact> contactList){
        if (contactList == null || contactList.isEmpty()){ return; }
        for (EContact contact : contactList){
            contacts.add(new ContactDTO(contact));
        }
    }
}
