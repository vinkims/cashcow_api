package com.example.cashcow_api.services.shop;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.repositories.ShopDAO;
import com.example.cashcow_api.services.farm.SFarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SShop {

    @Autowired private SFarm sFarm;
    
    @Autowired private ShopDAO shopDAO;

    public EShop create(ShopDTO shopDTO){

        EShop shop = new EShop();
        if (shopDTO.getName() != null){
            shop.setName(shopDTO.getName());
        }
        if (shopDTO.getLocation() != null){
            shop.setLocation(shopDTO.getLocation());
        }
        setFarm(shop, shopDTO.getFarmId());
        save(shop);
        return shop;
    }

    public Optional<EShop> getById(Integer shopId){
        return shopDAO.findById(shopId);
    }

    public List<EShop> getAll(){
        return shopDAO.findAll();
    }

    public void save(EShop shop){
        shopDAO.save(shop);
    }

    public void setFarm(EShop shop, Integer farmId){

        if (farmId == null){ return; }

        Optional<EFarm> farm = sFarm.getById(farmId);
        if (!farm.isPresent()){
            throw new NotFoundException("farm with specified id not found", "farmId");
        }
        shop.setFarm(farm.get());
    }
}
