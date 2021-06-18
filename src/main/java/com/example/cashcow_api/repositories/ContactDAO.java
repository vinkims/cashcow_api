package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EContact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDAO extends JpaRepository<EContact, Integer>{
    
}
