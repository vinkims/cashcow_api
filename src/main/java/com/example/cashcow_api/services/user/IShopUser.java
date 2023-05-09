package com.example.cashcow_api.services.user;

import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EShopUser;
import com.example.cashcow_api.models.EUser;

public interface IShopUser {
    
    EShopUser create(EShop shop, EUser user);

    void save(EShopUser shopUser);
}
