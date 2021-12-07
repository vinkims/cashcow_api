package com.example.cashcow_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.models.EMilkSale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MilkSaleDAO extends JpaRepository<EMilkSale, Integer>, JpaSpecificationExecutor<EMilkSale>{

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO(sum(ms.amount), cast(ms.createdOn as LocalDate), sum(ms.quantity), s.name) "
            + "FROM com.example.cashcow_api.models.EMilkSale ms "
            + "LEFT JOIN ms.shop s "
            + "WHERE ms.createdOn > :startDate "
                + "AND ms.createdOn < :endDate "
            + "GROUP BY cast(ms.createdOn as LocalDate)"
    )
    List<MilkSaleSummaryDTO> findMilkSaleSummary(LocalDateTime startDate, LocalDateTime endDate);
}
