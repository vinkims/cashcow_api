package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.EMilkingSession;

public interface IMilkingSession {
    
    List<EMilkingSession> getAll();
    
    Optional<EMilkingSession> getById(Integer sessionId);
}
