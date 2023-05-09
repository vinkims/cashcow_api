package com.example.cashcow_api.services.user;

import com.example.cashcow_api.dtos.user.UserMilkPriceDTO;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.models.EUserMilkPrice;

public interface IUserMilkPrice {
    
    EUserMilkPrice create(UserMilkPriceDTO userMilkPriceDTO, EUser user);

    void save(EUserMilkPrice userProfile);
}
