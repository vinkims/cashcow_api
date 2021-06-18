package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EUserProfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileDAO extends JpaRepository<EUserProfile, Integer>{
    
}
