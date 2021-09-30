package com.example.cashcow_api.services.shop;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.models.EShop;

public interface IShop {
    
    EShop create(ShopDTO shopDTO);

    Optional<EShop> getById(Integer shopId);

    List<EShop> getAll();

    void save(EShop shop);
}
