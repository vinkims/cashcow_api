package com.example.cashcow_api.services.shop;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.repositories.ShopDAO;
import com.example.cashcow_api.services.farm.IFarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SShop implements IShop{

    @Autowired private IFarm sFarm;
    
    @Autowired private ShopDAO shopDAO;

    @Override
    public Boolean checkExistsByName(String shopName){
        return shopDAO.existsByName(shopName);
    }

    @Override
    public EShop create(ShopDTO shopDTO){

        EShop shop = new EShop();
        shop.setCreatedOn(LocalDateTime.now());
        if (shopDTO.getName() != null){
            shop.setName(shopDTO.getName().toUpperCase());
        }
        if (shopDTO.getLocation() != null){
            shop.setLocation(shopDTO.getLocation());
        }
        setFarm(shop, shopDTO.getFarmId());
        save(shop);
        return shop;
    }

    @Override
    public Optional<EShop> getById(Integer shopId){
        return shopDAO.findById(shopId);
    }

    @Override
    public List<EShop> getAll(){
        return shopDAO.findAll();
    }

    @Override
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
