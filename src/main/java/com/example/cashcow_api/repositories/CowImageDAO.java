package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.ECowImage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CowImageDAO extends JpaRepository<ECowImage, Integer> {
    
}
