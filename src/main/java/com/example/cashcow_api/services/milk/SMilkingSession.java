package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.EMilkingSession;
import com.example.cashcow_api.repositories.MilkingSessionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMilkingSession implements IMilkingSession {

    @Autowired 
    private MilkingSessionDAO sessionDAO;

    @Override
    public List<EMilkingSession> getAll() {
        return sessionDAO.findAll();
    }

    @Override
    public Optional<EMilkingSession> getById(Integer sessionId) {
        return sessionDAO.findById(sessionId);
    }
    
}
