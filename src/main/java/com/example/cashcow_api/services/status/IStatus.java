package com.example.cashcow_api.services.status;

import java.util.Optional;

import com.example.cashcow_api.models.EStatus;

public interface IStatus {
    
    Optional<EStatus> getById(Integer statusId);

    EStatus getById(Integer statusId, Boolean handleException);
}
