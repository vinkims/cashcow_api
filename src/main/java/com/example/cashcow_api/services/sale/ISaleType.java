package com.example.cashcow_api.services.sale;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.sales.SaleTypeDTO;
import com.example.cashcow_api.models.ESaleType;

public interface ISaleType {
    
    ESaleType create(SaleTypeDTO saleTypeDTO);
    
    Optional<ESaleType> getById(Integer id);

    ESaleType getById(Integer id, Boolean handleException);

    List<ESaleType> getList();

    Page<ESaleType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ESaleType saleType);

    ESaleType update(Integer id, SaleTypeDTO saleTypeDTO);
}
