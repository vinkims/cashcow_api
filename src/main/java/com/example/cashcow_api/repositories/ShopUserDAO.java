package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cashcow_api.models.EShopUser;
import com.example.cashcow_api.models.composites.ShopUserPK;

public interface ShopUserDAO extends JpaRepository<EShopUser, ShopUserPK> {
    
}
