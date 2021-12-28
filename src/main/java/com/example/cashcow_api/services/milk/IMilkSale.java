package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleTotalDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO;
import com.example.cashcow_api.models.EMilkSale;

import org.springframework.data.domain.Page;

public interface IMilkSale {
    
    EMilkSale create(MilkSaleDTO saleDTO);

    List<CustomerSaleSummaryDTO> getCustomerSaleSummary(LocalDateTime startDate, LocalDateTime endDate, Integer customerId);

    List<CustomerSaleTotalDTO> getCustomerSaleTotal(LocalDateTime startDate, LocalDateTime endDate, Integer customerId);

    Optional<EMilkSale> getById(Integer saleId);

    List<MilkSaleSummaryDTO> getMilkSaleSummary(LocalDateTime startDate, LocalDateTime endDate);

    List<MilkSaleSummaryDTO> getMilkSaleSummaryByShop(LocalDateTime startDate, LocalDateTime endDate, Integer shopId);

    List<MilkSaleTotalDTO> getMilkSaleTotal(LocalDateTime startDate, LocalDateTime endDate);

    Page<EMilkSale> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EMilkSale sale);

    EMilkSale update(EMilkSale sale, MilkSaleDTO saleDTO);
}
