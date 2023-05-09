package com.example.cashcow_api.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EShopUser;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.ShopUserDAO;

@Service
public class SShopUser implements IShopUser {

    @Autowired
    private ShopUserDAO shopUserDAO;

    @Override
    public EShopUser create(EShop shop, EUser user) {
        EShopUser shopUser = new EShopUser(shop, user);
        save(shopUser);
        return shopUser;
    }

    @Override
    public void save(EShopUser shopUser) {
        shopUserDAO.save(shopUser);
    }
    
}
