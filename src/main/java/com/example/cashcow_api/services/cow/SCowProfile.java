package com.example.cashcow_api.services.cow;

import java.time.LocalDate;

import com.example.cashcow_api.dtos.cow.CowProfileDTO;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowProfile;
import com.example.cashcow_api.repositories.CowProfileDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCowProfile {
    
    @Autowired
    private CowProfileDAO cowProfileDAO;

    public ECowProfile create(CowProfileDTO cowProfileDTO, ECow cow){

        ECowProfile profile = cow.getProfile() != null ? cow.getProfile() : new ECowProfile();
        profile.setCow(cow);
        setBreed(profile, cowProfileDTO);
        setColor(profile, cowProfileDTO);
        setDateOfBirth(profile, cowProfileDTO);
        setDateOfDeath(profile, cowProfileDTO);
        setDateOfPurchase(profile, cowProfileDTO);
        setDateOfDeath(profile, cowProfileDTO);
        setLocationBought(profile, cowProfileDTO);
        setPurchaseAmount(profile, cowProfileDTO);
        setSaleAmount(profile, cowProfileDTO);

        return profile;
    }

    public void save(ECowProfile cowProfile){
        cowProfileDAO.save(cowProfile);
    }

    public void setBreed(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getBreed() != null){
            cowProfile.setBreed(cowProfileDTO.getBreed());
        }
    }

    public void setColor(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getColor() != null){
            cowProfile.setColor(cowProfileDTO.getColor());
        }
    }

    public void setDateOfBirth(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getDateOfBirth() != null){
            cowProfile.setDateOfBirth(LocalDate.parse(cowProfileDTO.getDateOfBirth()));
        }
    }

    public void setDateOfDeath(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getDateOfDeath() != null){
            cowProfile.setDateOfDeath(LocalDate.parse(cowProfileDTO.getDateOfDeath()));
        }
    }

    public void setDateOfPurchase(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getDateOfPurchase() != null){
            cowProfile.setDateOfPurchase(LocalDate.parse(cowProfileDTO.getDateOfPurchase()));
        }
    }

    public void setDateOfSale(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getDateOfSale() != null){
            cowProfile.setDateOfSale(LocalDate.parse(cowProfileDTO.getDateOfSale()));
        }
    }

    public void setLocationBought(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getLocationBought() != null){
            cowProfile.setLocationBought(cowProfileDTO.getLocationBought());
        }
    }

    public void setPurchaseAmount(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getPurchaseAmount() != null){
            cowProfile.setPurchaseAmount(cowProfileDTO.getPurchaseAmount());
        }
    }

    public void setSaleAmount(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getSaleAmount() != null){
            cowProfile.setSaleAmount(cowProfileDTO.getSaleAmount());
        }
    }
}

