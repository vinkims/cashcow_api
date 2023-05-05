package com.example.cashcow_api.services.purchase;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.purchases.PurchaseDTO;
import com.example.cashcow_api.models.EPurchase;

import org.springframework.data.domain.Page;

public interface IPurchase {
    
    EPurchase create(PurchaseDTO purchaseDTO);

    Optional<EPurchase> getById(Integer purchaseId);

    EPurchase getById(Integer purchaseId, Boolean handleExcpetion);

    Page<EPurchase> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EPurchase purchase);

    EPurchase update(Integer purchaseId, PurchaseDTO purchaseDTO);
}
