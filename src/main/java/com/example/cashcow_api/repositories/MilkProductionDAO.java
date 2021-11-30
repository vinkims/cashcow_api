package com.example.cashcow_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.models.EMilkProduction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MilkProductionDAO extends JpaRepository<EMilkProduction, Integer>, JpaSpecificationExecutor<EMilkProduction>{

    @Query(
        value =  "SELECT new com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO(cast(m.createdOn as LocalDate), sum(m.quantity)) "
            + "FROM com.example.cashcow_api.models.EMilkProduction m "
            + "WHERE m.createdOn > :startDate "
                + "AND m.createdOn < :endDate "
            + "GROUP BY cast(m.createdOn as LocalDate) "
            + "ORDER BY cast(m.createdOn as LocalDate) DESC"
    )
    List<MilkProductionSummaryDTO> findMilkProductionSummary(LocalDateTime startDate, LocalDateTime endDate);
    
}
