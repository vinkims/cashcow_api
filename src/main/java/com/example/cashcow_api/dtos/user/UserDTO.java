package com.example.cashcow_api.dtos.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.role.RoleDTO;
import com.example.cashcow_api.models.EContact;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.models.EUserExpense;
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

    private Integer id;

    private BigDecimal balance;
    
    private List<@Valid ContactDTO> contacts = new ArrayList<ContactDTO>();

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private String firstName;

    private String lastName;

    private String middleName;

    private ShopUserDTO shopUser;

    private UserMilkPriceDTO userMilkPrice;

    private String passcode;

    @JsonIgnoreProperties("description")
    private RoleDTO role;

    private Integer roleId;

    private String status;

    private Integer statusId;

    private List<UserExpenseDTO> userExpenses;

    public UserDTO(EUser user){
        setBalance(user.getBalance());
        setContactData(user.getContacts());
        setCreatedOn(user.getCreatedOn());
        if (user.getFarm() != null){
            setFarm(new FarmDTO(user.getFarm()));
        }
        this.setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setId(user.getId());
        setMiddleName(user.getMiddleName());
        setRole(new RoleDTO(user.getRole()));
        if (user.getShopUser() != null) {
            setShopUser(new ShopUserDTO(user.getShopUser()));
        }
        setStatus(user.getStatus().getName());
        setUpdatedOn(user.getUpdatedOn());
        setUserMilkPrice(new UserMilkPriceDTO(user.getUserMilkPrice()));
    }

    public void setContactData(List<EContact> contactList){
        if (contactList == null || contactList.isEmpty()){ return; }
        for (EContact contact : contactList){
            contacts.add(new ContactDTO(contact));
        }
    }

    public void setUserExpensesData(List<EUserExpense> userExpenseList) {
        if (userExpenseList == null || userExpenseList.isEmpty()) { return; }

        userExpenses = new ArrayList<>();
        for (EUserExpense userExpense : userExpenseList) {
            userExpenses.add(new UserExpenseDTO(userExpense));
        }
    }
}
