package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.cow.CowSaleDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowSale;

public interface ICowSale {
    
    ECowSale create(CowSaleDTO cowSaleDTO);

    Optional<ECowSale> getById(Integer saleId);

    ECowSale getById(Integer saleId, Boolean handleException);

    Page<ECowSale> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECowSale cowSale);

    ECowSale update(Integer saleId, CowSaleDTO cowSaleDTO);
}
