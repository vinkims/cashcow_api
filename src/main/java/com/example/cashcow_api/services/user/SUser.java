package com.example.cashcow_api.services.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.dtos.user.UserDTO;
import com.example.cashcow_api.dtos.user.UserProfileDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EContact;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.ERole;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.models.EUserProfile;
import com.example.cashcow_api.repositories.UserDAO;
import com.example.cashcow_api.services.contact.SContact;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.role.IRole;
import com.example.cashcow_api.services.shop.IShop;
import com.example.cashcow_api.services.status.IStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SUser implements IUser {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;
    
    @Autowired private UserDAO userDAO;

    @Autowired private SContact sContact;

    @Autowired private IFarm sFarm;

    @Autowired private IRole sRole;

    @Autowired private IShop sShop;

    @Autowired private IStatus sStatus;

    @Autowired private SUserProfile sUserProfile;

    /**
     * Create EUser obj
     * @param userDTO
     * @return
     */
    @Override
    public EUser create(UserDTO userDTO){

        EUser user = new EUser();
        user.setCreatedOn(LocalDateTime.now());
        user.setFirstName(userDTO.getFirstName());
        if (userDTO.getMiddleName() != null){
            user.setMiddleName(userDTO.getMiddleName());
        }
        if (userDTO.getLastName() != null){
            user.setLastName(userDTO.getLastName());
        }
        setFarm(user, userDTO.getFarmId());
        setRole(user, userDTO.getRoleId());
        setShop(user, userDTO.getShopId());

        Integer statusId = userDTO.getStatusId() != null ? userDTO.getStatusId() : activeStatusId;
        setStatus(user, statusId);

        save(user);

        setContactData(user, userDTO.getContacts());
        setProfile(user, userDTO.getProfile());

        return user;
    }

    /**
     * Set contacts
     * @param user
     * @param contactList
     */
    public void setContactData(EUser user, List<ContactDTO> contactList){

        List<EContact> contacts = new ArrayList<>();
        for (ContactDTO contactDTO : contactList){
            EContact contact = sContact.create(user, contactDTO);
            sContact.save(contact);
            contacts.add(contact);
        }
        user.setContacts(contacts);
    }

    public void setProfile(EUser user, UserProfileDTO userProfileDTO){
        if (userProfileDTO != null){
            EUserProfile profile = sUserProfile.create(userProfileDTO, user);
            sUserProfile.save(profile);
            user.setProfile(profile);
        }
    }

    public void setFarm(EUser user, Integer farmId){
        
        if (farmId == null){ return; }

        Optional<EFarm> farm = sFarm.getById(farmId);
        if (!farm.isPresent()){
            throw new NotFoundException("farm with specified id not found", "farmId");
        }
        user.setFarm(farm.get());
    }

    /**
     * Set user role
     * @param user
     * @param roleId
     */
    public void setRole(EUser user, Integer roleId){
        
        if (roleId == null){ return ;}

        Optional<ERole> role = sRole.getById(roleId);
        if (!role.isPresent()){
            throw new NotFoundException("user role not found", "roleId");
        }
        user.setRole(role.get());
    }

    /**
     * Set shop
     * @param user
     * @param shopId
     */
    public void setShop(EUser user, Integer shopId){

        if (shopId == null){ return; }

        Optional<EShop> shop = sShop.getById(shopId);
        if (!shop.isPresent()){
            throw new NotFoundException("shop with specified id not found", "shopId");
        }
        user.setShop(shop.get());
    }

    public void setStatus(EUser user, Integer statusId){
        
        Optional<EStatus> status = sStatus.getById(statusId);
        if (!status.isPresent()){
            throw new NotFoundException("Status with specified id not found", "statusId");
        }
        user.setStatus(status.get());
    }

    /**
     * Get user by id
     * @param userId
     * @return
     */
    @Override
    public Optional<EUser> getById(Integer userId){
        return userDAO.findById(userId);
    }

    @Override
    public Optional<EUser> getByContactValue(String contactValue){
        return userDAO.findByContactValue(contactValue);
    }

    /**
     * Get a list of all users
     * @return
     */
    @Override
    public List<EUser> getAllUsers(){
        return userDAO.findAll();
    }

    /**
     * Persist EUser obj to db
     * @param user
     */
    @Override
    public void save(EUser user){
        userDAO.save(user);
    }
}
