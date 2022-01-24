package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.ETransactionType;

public interface ITransactionType {
 
    Optional<ETransactionType> getById(Integer id);

    List<ETransactionType> getList();
}
