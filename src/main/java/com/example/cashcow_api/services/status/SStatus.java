package com.example.cashcow_api.services.status;

import java.util.Optional;

import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.StatusDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SStatus implements IStatus {
    
    @Autowired private StatusDAO statusDAO;

    @Override
    public Optional<EStatus> getById(Integer statusId) {
        return statusDAO.findById(statusId);
    }

    @Override
    public EStatus getById(Integer statusId, Boolean handleException) {
        Optional<EStatus> status = getById(statusId);
        if (!status.isPresent() && handleException) {
            throw new NotFoundException("status with specified id not found", "statusId");
        }
        return status.get();
    }
}
