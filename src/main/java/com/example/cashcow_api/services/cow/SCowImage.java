package com.example.cashcow_api.services.cow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowImageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECowImage;
import com.example.cashcow_api.repositories.CowImageDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCowImage implements ICowImage {

    @Autowired
    private CowImageDAO cowImageDAO;

    @Override
    public ECowImage create(CowImageDTO cowImageDTO) {
        
        ECowImage cowImage = new ECowImage();
        cowImage.setCreatedOn(LocalDateTime.now());
        cowImage.setImage(cowImageDTO.getImage());
        save(cowImage);
        return cowImage;
    }

    @Override
    public Optional<ECowImage> getById(Integer id) {
        return cowImageDAO.findById(id);
    }

    @Override
    public ECowImage getById(Integer id, Boolean handleException) {
        Optional<ECowImage> image = getById(id);
        if (!image.isPresent() && handleException) {
            throw new NotFoundException("image with specified id not found", "cowImageId");
        }
        return image.get();
    }

    @Override
    public List<ECowImage> getList() {
        return cowImageDAO.findAll();
    }

    @Override
    public void save(ECowImage cowImage) {
        cowImageDAO.save(cowImage);
    }
    
}
