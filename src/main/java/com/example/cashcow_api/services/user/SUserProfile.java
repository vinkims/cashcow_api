package com.example.cashcow_api.services.user;

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

        setPasscode(profile, userProfileDTO);
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
}
