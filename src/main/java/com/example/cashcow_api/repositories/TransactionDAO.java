package com.example.cashcow_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.transaction.TransactionSummaryDTO;
import com.example.cashcow_api.models.ETransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TransactionDAO extends JpaRepository<ETransaction, Integer>, JpaSpecificationExecutor<ETransaction> {

    @Query(
        value = "SELECT * FROM transactions t "
            + "WHERE t.transaction_type_id IN(:expenseTypes)",
        nativeQuery = true
    )
    Page<ETransaction> findExpenses(List<Integer> expenseTypes, Pageable pageable);

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.transaction.TransactionSummaryDTO(sum(t.amount), tt.name) "
            + "FROM com.example.cashcow_api.models.ETransaction t "
            + "LEFT JOIN t.transactionType tt "
            + "WHERE t.createdOn > :startDate "
                + "AND t.createdOn < :endDate "
            + "GROUP BY tt.name "
            + "ORDER BY tt.name"
    )
    List<TransactionSummaryDTO> findTransactionSummary(LocalDateTime startDate, LocalDateTime endDate);
}
