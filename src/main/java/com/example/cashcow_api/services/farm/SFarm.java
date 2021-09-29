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
public class SFarm {
    
    @Autowired private FarmDAO farmDAO;

    public EFarm create(FarmDTO farmDTO){
        
        EFarm farm = new EFarm();
        farm.setName(farmDTO.getName());
        farm.setCreatedOn(LocalDateTime.now());

        return farm;
    }

    public List<EFarm> getAll(){
        return farmDAO.findAll();
    }

    public Optional<EFarm> getById(Integer farmId){
        return farmDAO.findById(farmId);
    }

    public void save(EFarm farm){
        farmDAO.save(farm);
    }
}
