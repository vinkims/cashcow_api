package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleDTO;
import com.example.cashcow_api.models.EMilkSale;

import org.springframework.data.domain.Page;

public interface IMilkSale {
    
    EMilkSale create(MilkSaleDTO saleDTO);

    Optional<EMilkSale> getById(Integer saleId);

    Page<EMilkSale> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EMilkSale sale);

    EMilkSale update(EMilkSale sale, MilkSaleDTO saleDTO);
}
