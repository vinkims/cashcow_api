package com.example.cashcow_api.services.user;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.user.ShopUserDTO;
import com.example.cashcow_api.dtos.user.SummaryUserDTO;
import com.example.cashcow_api.dtos.user.UserDTO;
import com.example.cashcow_api.dtos.user.UserMilkPriceDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EContact;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.ERole;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EShopUser;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.models.EUserMilkPrice;
import com.example.cashcow_api.repositories.UserDAO;
import com.example.cashcow_api.services.contact.IContact;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.role.IRole;
import com.example.cashcow_api.services.shop.IShop;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SUser implements IUser {

    Logger logger = LoggerFactory.getLogger(SUser.class);

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Value(value = "${default.value.role.admin-role-id}")
    private Integer adminRoleId;

    @Value(value = "${default.value.role.farm-attendant-role-id}")
    private Integer farmAttendantRoleId;

    @Value(value = "${default.value.contact.mobile-type-id}")
    private Integer mobileTypeId;

    @Value(value = "${default.value.role.shop-attendant-role-id}")
    private Integer shopAttendantRoleId;
    
    @Autowired 
    private UserDAO userDAO;

    @Autowired 
    private IContact sContact;

    @Autowired 
    private IFarm sFarm;

    @Autowired
    private SpecFactory specFactory;

    @Autowired 
    private IRole sRole;

    @Autowired 
    private IShop sShop;

    @Autowired
    private IShopUser sShopUser;

    @Autowired 
    private IStatus sStatus;

    @Autowired
    private IUserMilkPrice sUserMilkPrice;

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
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setPasscode(userDTO.getPasscode());
        setFarm(user, userDTO.getFarmId());
        setRole(user, userDTO.getRoleId());
        setShop(user, userDTO.getShopId());

        Integer statusId = userDTO.getStatusId() != null ? userDTO.getStatusId() : activeStatusId;
        setStatus(user, statusId);

        save(user);

        setContactData(user, userDTO.getContacts());
        setShopUser(user, userDTO.getShopUser());
        setUserMilkPrice(user, userDTO.getUserMilkPrice());

        return user;
    }

    public Boolean checkContactExists(EUser user){

        Optional<EContact> userContact = sContact.getByUserAndContactType(
            user.getId(), mobileTypeId);
        if (userContact.isPresent()){
            return true;
        }

        return false;
    }

    public void deleteContact(EUser user, List <ContactDTO> contactList){
        List<EContact> contacts = user.getContacts();
        if (contacts != null){
            for (EContact contact : contacts){
                sContact.deleteContact(contact);
            }
        }
        setContactData(user, contactList);
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
    public EUser getById(Integer userId, Boolean handleException) {
        Optional<EUser> user = getById(userId);
        if (!user.isPresent() && handleException) {
            throw new NotFoundException("user with specified id not found", "userId");
        }
        return user.get();
    }

    @Override
    public Optional<EUser> getByContactValue(String contactValue){
        return userDAO.findByContactValue(contactValue);
    }

    @Override
    public Optional<EUser> getByIdOrContact(String userValue){

        Integer userId;
        try {
            userId = Integer.valueOf(userValue);
        } catch (NumberFormatException e){
            userId = (Integer) null;
            logger.info("\nERROR: [SUser.getByIdOrContact] | [MSG] - {}", e.getMessage());
            return getByContactValue(userValue);
        }

        return userDAO.findByIdOrContactValue(userId, userValue);
    }

    @Override
    public EUser getByIdOrContact(String userValue, Boolean handleException) {
        Optional<EUser> user = getByIdOrContact(userValue);
        if (!user.isPresent() && handleException) {
            throw new NotFoundException("user with specified id or contact value not found", "userValue");
        }
        return user.get();
    }

    public String getFirstContact(List<EContact> contactsList){

        Optional<EContact> contact = contactsList
            .stream()
            .filter(cont -> cont.getContactType().getId().equals(mobileTypeId))
            .findFirst();
        return contact.map(EContact::getValue).orElse(null);
    }

    public String getFirstContactFromApp(List<ContactDTO> contacts){

        Optional<ContactDTO> contact = contacts
            .stream()
            .filter(cont -> cont.getContactTypeId().equals(mobileTypeId))
            .findFirst();
        return contact.map(ContactDTO::getContactValue).orElse(null);
    }

    /**
     * Get a paginated list of all users
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Page<EUser> getPaginatedList(PageDTO pageDTO, List<String> allowableFields){

        String search = pageDTO.getSearch();

        SpecBuilder<EUser> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EUser>) specFactory.generateSpecification(search, specBuilder, allowableFields, "user");

        Specification<EUser> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return userDAO.findAll(spec, pageRequest);
    }

    @Override
    public List<EUser> getSystemUsers(){
        List<Integer> systemRoles = new ArrayList<>(
            Arrays.asList(adminRoleId, farmAttendantRoleId, shopAttendantRoleId)
        );
        return userDAO.findSystemUsers(systemRoles);
    }

    @Override
    public List<SummaryUserDTO> getUserCountPerRole(){
        return userDAO.findUserCountByRole();
    }

    /**
     * Persist EUser obj to db
     * @param user
     */
    @Override
    public void save(EUser user){
        userDAO.save(user);
    }

    /**
     * Set contacts
     * @param user
     * @param contactList
     */
    public void setContactData(EUser user, List<ContactDTO> contactList){

        if (contactList != null){
            List<EContact> contacts = new ArrayList<>();
            for (ContactDTO contactDTO : contactList){
                EContact contact = sContact.create(user, contactDTO);
                sContact.save(contact);
                contacts.add(contact);
            }
            user.setContacts(contacts);
        }
    }

    public void setFarm(EUser user, Integer farmId){
        if (farmId == null){ return; }

        EFarm farm = sFarm.getById(farmId, true);
        user.setFarm(farm);
    }

    /**
     * Set user role
     * @param user
     * @param roleId
     */
    public void setRole(EUser user, Integer roleId){
        if (roleId == null){ return ;}

        ERole role = sRole.getById(roleId, true);
        user.setRole(role);
    }

    /**
     * Set shop
     * @param user
     * @param shopId
     */
    public void setShop(EUser user, Integer shopId){
        if (shopId == null){ return; }

        EShop shop = sShop.getById(shopId, true);
        user.setShop(shop);
    }

    public void setShopUser(EUser user, ShopUserDTO shopUserDTO) {
        if (shopUserDTO == null) { return; }

        EShop shop = sShop.getById(shopUserDTO.getShopId(), true);
        EShopUser shopUser = sShopUser.create(shop, user);
        user.setShopUser(shopUser);
    }

    public void setStatus(EUser user, Integer statusId){
        if (statusId == null) { return; }
        
        EStatus status = sStatus.getById(statusId, true);
        user.setStatus(status);
    }

    public void setUserMilkPrice(EUser user, UserMilkPriceDTO userMilkPriceDTO) {
        if (userMilkPriceDTO == null) { return; }

        EUserMilkPrice userMilkPrice = sUserMilkPrice.create(userMilkPriceDTO, user);
        user.setUserMilkPrice(userMilkPrice);
    }

    @Override
    public EUser update(EUser user, UserDTO userDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException{

        String[] fields = {"FirstName", "MiddleName", "LastName", "Balance"};
        for (String field : fields){
            Method getField = UserDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(userDTO);

            if (fieldValue != null){
                fieldValue = fieldValue.getClass().equals(String.class) ? 
                    ((String) fieldValue).trim() : fieldValue;
                EUser.class.getMethod("set" + field, fieldValue.getClass()).invoke(user, fieldValue);
            }
        }
        user.setUpdatedOn(LocalDateTime.now());
        setShop(user, userDTO.getShopId());
        setStatus(user, userDTO.getStatusId());

        save(user);

        //if (userDTO.getContacts() != null){
        //    deleteContact(user, userDTO.getContacts());
        //}
        setContactData(user, userDTO.getContacts());
        setRole(user, userDTO.getRoleId());

        return user;
    } 

    @Override
    public EUser update(String userValue, UserDTO userDTO) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EUser user = getByIdOrContact(userValue, true);

        String[] fields = {"FirstName", "MiddleName", "LastName", "Balance"};
        for (String field : fields){
            Method getField = UserDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(userDTO);

            if (fieldValue != null){
                fieldValue = fieldValue.getClass().equals(String.class) ? 
                    ((String) fieldValue).trim() : fieldValue;
                EUser.class.getMethod("set" + field, fieldValue.getClass()).invoke(user, fieldValue);
            }
        }
        user.setUpdatedOn(LocalDateTime.now());
        setRole(user, userDTO.getRoleId());
        setShop(user, userDTO.getShopId());
        setStatus(user, userDTO.getStatusId());

        save(user);

        setContactData(user, userDTO.getContacts());
        setShopUser(user, userDTO.getShopUser());
        setUserMilkPrice(user, userDTO.getUserMilkPrice());

        return user;
    }
}
