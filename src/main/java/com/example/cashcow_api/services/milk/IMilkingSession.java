package com.example.cashcow_api.services.milk;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkingSessionDTO;
import com.example.cashcow_api.models.EMilkingSession;

public interface IMilkingSession {
    
    EMilkingSession create(MilkingSessionDTO milkingSessionDTO);

    List<EMilkingSession> getAll();
    
    Optional<EMilkingSession> getById(Integer sessionId);

    EMilkingSession getById(Integer sessionId, Boolean handleException);

    Page<EMilkingSession> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EMilkingSession milkingSession);

    EMilkingSession update(Integer sessionId, MilkingSessionDTO milkingSessionDTO);
}
