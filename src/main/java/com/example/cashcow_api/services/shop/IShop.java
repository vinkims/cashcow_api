package com.example.cashcow_api.services.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.models.EShop;

public interface IShop {
    
    Boolean checkExistsByName(String shopName);
    
    EShop create(ShopDTO shopDTO);

    List<EShop> getAll();

    Optional<EShop> getById(Integer shopId);

    EShop getById(Integer shopId, Boolean handleException);

    Page<EShop> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EShop shop);

    EShop update(Integer shopId, ShopDTO shopDTO);
}
