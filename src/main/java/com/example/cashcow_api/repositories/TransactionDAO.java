package com.example.cashcow_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.transaction.EmployeeTransactionDTO;
import com.example.cashcow_api.dtos.transaction.TransactionSummaryDTO;
import com.example.cashcow_api.models.ETransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TransactionDAO extends JpaRepository<ETransaction, Integer>, JpaSpecificationExecutor<ETransaction> {
    
    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.transaction.EmployeeTransactionDTO(t.amount, cast(t.createdOn as LocalDate), tt.name, a.firstName) "
            + "FROM com.example.cashcow_api.models.ETransaction t "
            + "LEFT JOIN t.transactionType tt "
            + "LEFT JOIN t.attendant a "
            + "WHERE t.createdOn > :startDate "
                + "AND t.createdOn < :endDate "
                + "AND (tt.id = :advanceTypeId OR tt.id = :salaryTypeId) "
                + "AND a.id = :userId "
            + "GROUP BY cast(t.createdOn as LocalDate), amount, tt.name, a.firstName"
    )
    List<EmployeeTransactionDTO> findEmployeeExpenses(LocalDateTime startDate, LocalDateTime endDate, 
            Integer advanceTypeId, Integer salaryTypeId, Integer userId);

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.transaction.EmployeeTransactionDTO(t.amount, cast(t.createdOn as LocalDate), tt.name, a.firstName) "
            + "FROM com.example.cashcow_api.models.ETransaction t "
            + "LEFT JOIN t.transactionType tt "
            + "LEFT JOIN t.attendant a "
            + "WHERE t.createdOn > :startDate "
                + "AND t.createdOn < :endDate "
                + "AND (tt.id = :advanceTypeId OR tt.id = :salaryTypeId) "
        + "GROUP BY cast(t.createdOn as LocalDate), amount, tt.name, a.firstName"
    )
    List<EmployeeTransactionDTO> findAllEmployeeExpenses(LocalDateTime startDate, LocalDateTime endDate, Integer advanceTypeId, Integer salaryTypeId);

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
