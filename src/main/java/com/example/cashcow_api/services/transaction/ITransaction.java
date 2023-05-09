package com.example.cashcow_api.services.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.dtos.transaction.TransactionSummaryDTO;
import com.example.cashcow_api.models.ETransaction;

import org.springframework.data.domain.Page;

public interface ITransaction {
    
    ETransaction create(TransactionDTO transactionDTO);

    Page<ETransaction> getAllExpenses(PageDTO pageDTO);

    Optional<ETransaction> getById(Integer transactionId);

    ETransaction getById(Integer transactionId, Boolean handleException);

    Page<ETransaction> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    List<TransactionSummaryDTO> getTransactionSummary(LocalDateTime startDate, LocalDateTime endDate);

    void save(ETransaction transaction);

    ETransaction update(Integer transactionId, TransactionDTO transactionDTO);
}
