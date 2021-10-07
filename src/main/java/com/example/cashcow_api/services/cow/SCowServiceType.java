package com.example.cashcow_api.services.cow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowServiceTypeDTO;
import com.example.cashcow_api.models.ECowServiceType;
import com.example.cashcow_api.repositories.CowServiceTypeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCowServiceType implements ICowServiceType {

    @Autowired
    private CowServiceTypeDAO serviceTypeDAO;

    @Override
    public Boolean checkExistsByName(String name) {
        return serviceTypeDAO.existsByName(name);
    }

    @Override
    public ECowServiceType create(CowServiceTypeDTO serviceTypeDTO) {
        
        ECowServiceType serviceType = new ECowServiceType();
        serviceType.setCreatedOn(LocalDateTime.now());
        serviceType.setDescription(serviceTypeDTO.getDescription());
        serviceType.setName(serviceTypeDTO.getName().toUpperCase());

        save(serviceType);

        return serviceType;
    }

    @Override
    public List<ECowServiceType> getAll() {
        return serviceTypeDAO.findAll();
    }

    @Override
    public Optional<ECowServiceType> getById(Integer serviceTypeId) {
        return serviceTypeDAO.findById(serviceTypeId);
    }

    @Override
    public void save(ECowServiceType serviceType) {
        serviceTypeDAO.save(serviceType);
    }
    
}
