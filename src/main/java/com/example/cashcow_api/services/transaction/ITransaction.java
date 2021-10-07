package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.models.ETransaction;

import org.springframework.data.domain.Page;

public interface ITransaction {
    
    ETransaction create(TransactionDTO transactionDTO);

    Optional<ETransaction> getById(Integer transactionId);

    Page<ETransaction> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ETransaction transaction);

    ETransaction update(ETransaction transaction, TransactionDTO transactionDTO);
}
