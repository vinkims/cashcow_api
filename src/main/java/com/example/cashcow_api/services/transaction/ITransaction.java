package com.example.cashcow_api.services.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.EmployeeTransactionDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.dtos.transaction.TransactionSummaryDTO;
import com.example.cashcow_api.models.ETransaction;

import org.springframework.data.domain.Page;

public interface ITransaction {
    
    ETransaction create(TransactionDTO transactionDTO);

    Optional<ETransaction> getById(Integer transactionId);

    List<EmployeeTransactionDTO> getEmployeeExpenses(LocalDateTime startDate, LocalDateTime endDate, Integer userId);

    Page<ETransaction> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    List<TransactionSummaryDTO> getTransactionSummary(LocalDateTime startDate, LocalDateTime endDate);

    void save(ETransaction transaction);

    ETransaction update(ETransaction transaction, TransactionDTO transactionDTO);
}
