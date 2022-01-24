package com.example.cashcow_api.services.user;

import java.time.LocalDate;

import com.example.cashcow_api.dtos.user.UserProfileDTO;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.models.EUserProfile;
import com.example.cashcow_api.repositories.UserProfileDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SUserProfile {
    
    @Autowired private UserProfileDAO userProfileDAO;

    public EUserProfile create(UserProfileDTO userProfileDTO, EUser user){

        EUserProfile profile = user.getProfile() != null ? user.getProfile() : new EUserProfile();
        if (userProfileDTO.getMilkPrice() != null){
            profile.setMilkPrice(userProfileDTO.getMilkPrice());
        }
        setPasscode(profile, userProfileDTO);
        setPriceEndDate(profile, userProfileDTO);
        setPriceStartDate(profile, userProfileDTO);
        profile.setUser(user);

        return profile;
    }

    /**
     * Persist EUserProfile obj to db
     */
    public void save(EUserProfile userProfile){
        userProfileDAO.save(userProfile);
    }

    /**
     * Sets passcode
     * @param userProfile
     * @param userProfileDTO
     */
    public void setPasscode(EUserProfile userProfile, UserProfileDTO userProfileDTO){
        if (userProfileDTO.getPasscode() != null){
            userProfile.setPasscode(userProfileDTO.getPasscode());
        }
    }

    public void setPriceEndDate(EUserProfile profile, UserProfileDTO userProfileDTO){
        if (userProfileDTO.getPriceExpiresOn() != null){
            profile.setPriceExpiresOn(LocalDate.parse(userProfileDTO.getPriceExpiresOn()));
        }
    }

    public void setPriceStartDate(EUserProfile profile, UserProfileDTO userProfileDTO){
        if (userProfileDTO.getPriceValidOn() != null){
            profile.setPriceValidOn(LocalDate.parse(userProfileDTO.getPriceValidOn()));
        }
    }
}
