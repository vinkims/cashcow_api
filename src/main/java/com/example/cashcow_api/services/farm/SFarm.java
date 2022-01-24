package com.example.cashcow_api.services.farm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.repositories.FarmDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SFarm implements IFarm {
    
    @Autowired private FarmDAO farmDAO;

    @Override
    public Boolean checkExistsByName(String farmName){
        return farmDAO.existsByName(farmName);
    }

    @Override
    public EFarm create(FarmDTO farmDTO){
        
        EFarm farm = new EFarm();
        farm.setName(farmDTO.getName().toUpperCase());
        farm.setCreatedOn(LocalDateTime.now());
        save(farm);

        return farm;
    }

    @Override
    public List<EFarm> getAll(){
        return farmDAO.findAll();
    }

    @Override
    public Optional<EFarm> getById(Integer farmId){
        return farmDAO.findById(farmId);
    }

    @Override
    public void save(EFarm farm){
        farmDAO.save(farm);
    }
}
