package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowPurchaseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowPurchase;

public interface ICowPurchase {
    
    ECowPurchase create(CowPurchaseDTO cowPurchaseDTO);

    Optional<ECowPurchase> getById(Integer id);

    ECowPurchase getById(Integer id, Boolean handleException);

    Page<ECowPurchase> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowPurchase cowPurchase);

    ECowPurchase update(Integer id, CowPurchaseDTO cowPurchaseDTO);
}
