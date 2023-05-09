package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionTypeDTO;
import com.example.cashcow_api.models.ETransactionType;

public interface ITransactionType {
 
    ETransactionType create(TransactionTypeDTO transactionTypeDTO);

    Optional<ETransactionType> getById(Integer id);

    ETransactionType getById(Integer id, Boolean handleException);

    List<ETransactionType> getList();

    Page<ETransactionType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ETransactionType transactionType);

    ETransactionType update(Integer id, TransactionTypeDTO transactionTypeDTO);
}
