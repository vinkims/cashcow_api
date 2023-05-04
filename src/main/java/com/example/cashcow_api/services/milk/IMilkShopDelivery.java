package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkShopDeliveryDTO;
import com.example.cashcow_api.models.EMilkShopDelivery;

import org.springframework.data.domain.Page;

public interface IMilkShopDelivery {
    
    EMilkShopDelivery create(MilkShopDeliveryDTO milkDeliveryDTO);

    Optional<EMilkShopDelivery> getById(Integer deliveryId);

    EMilkShopDelivery getById(Integer deliveryId, Boolean handleException);

    Page<EMilkShopDelivery> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EMilkShopDelivery delivery);

    EMilkShopDelivery update(Integer deliveryId, MilkShopDeliveryDTO deliveryDTO);
}
