package com.example.cashcow_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
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
            + "ORDER BY cast(m.createdOn as LocalDate) ASC"
    )
    List<MilkProductionSummaryDTO> findMilkProductionSummary(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.DailyCowProductionDTO(m.quantity, s.name) "
            + "FROM com.example.cashcow_api.models.EMilkProduction m "
            + "LEFT JOIN m.cow c "
            + "LEFT JOIN m.milkingSession s "
            + "WHERE m.createdOn > :startDate "
                + "AND m.createdOn < :endDate "
                + "AND c.id = :cowId "
            + "GROUP BY s.name, m.quantity"
    )
    List<DailyCowProductionDTO> findDailyProductionByCow(LocalDateTime startDate, LocalDateTime endDate, Integer cowId);

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO(cast(m.createdOn as LocalDate), sum(m.quantity)) "
            + "FROM com.example.cashcow_api.models.EMilkProduction m "
            + "LEFT JOIN m.cow c "
            + "WHERE m.createdOn > :startDate "
                + "AND m.createdOn < :endDate "
                + "AND c.id = :cowId "
            + "GROUP BY cast(m.createdOn as LocalDate) "
            + "ORDER BY cast(m.createdOn as LocalDate) ASC"
    )
    List<MilkProductionSummaryDTO> findProductionSummaryByCow(LocalDateTime startDate, LocalDateTime endDate, Integer cowId);
}
