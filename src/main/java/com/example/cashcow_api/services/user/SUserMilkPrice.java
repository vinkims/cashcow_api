package com.example.cashcow_api.services.user;

import java.time.LocalDate;

import com.example.cashcow_api.dtos.user.UserMilkPriceDTO;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.models.EUserMilkPrice;
import com.example.cashcow_api.repositories.UserMilkPriceDAO;
import com.example.cashcow_api.services.status.IStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SUserMilkPrice implements IUserMilkPrice {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private IStatus sStatus;
    
    @Autowired 
    private UserMilkPriceDAO userProfileDAO;

    @Override
    public EUserMilkPrice create(UserMilkPriceDTO userMilkPriceDTO, EUser user){

        EUserMilkPrice userMilkPrice = user.getUserMilkPrice() != null ? user.getUserMilkPrice() : new EUserMilkPrice();
        if (userMilkPriceDTO.getMilkPrice() != null){
            userMilkPrice.setMilkPrice(userMilkPriceDTO.getMilkPrice());
        }
        setPriceEndDate(userMilkPrice, userMilkPriceDTO);
        setPriceStartDate(userMilkPrice, userMilkPriceDTO);
        userMilkPrice.setUser(user);
        Integer status = userMilkPriceDTO.getStatusId() == null ? activeStatusId : userMilkPriceDTO.getStatusId();
        setStatus(userMilkPrice, status);

        save(userMilkPrice);
        return userMilkPrice;
    }

    /**
     * Persist EUserProfile obj to db
     */
    @Override
    public void save(EUserMilkPrice userProfile){
        userProfileDAO.save(userProfile);
    }

    public void setPriceEndDate(EUserMilkPrice profile, UserMilkPriceDTO userMilkPriceDTO){
        if (userMilkPriceDTO.getPriceExpiresOn() != null){
            profile.setPriceExpiresOn(LocalDate.parse(userMilkPriceDTO.getPriceExpiresOn()));
        }
    }

    public void setPriceStartDate(EUserMilkPrice profile, UserMilkPriceDTO userMilkPriceDTO){
        if (userMilkPriceDTO.getPriceValidOn() != null){
            profile.setPriceValidOn(LocalDate.parse(userMilkPriceDTO.getPriceValidOn()));
        }
    }

    public void setStatus(EUserMilkPrice userMilkPrice, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        userMilkPrice.setStatus(status);
    }
}
