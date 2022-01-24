package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkDeliveryDTO;
import com.example.cashcow_api.models.EMilkDelivery;

import org.springframework.data.domain.Page;

public interface IMilkDelivery {
    
    EMilkDelivery create(MilkDeliveryDTO milkDeliveryDTO);

    Optional<EMilkDelivery> getById(Integer deliveryId);

    Page<EMilkDelivery> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EMilkDelivery delivery);
}
