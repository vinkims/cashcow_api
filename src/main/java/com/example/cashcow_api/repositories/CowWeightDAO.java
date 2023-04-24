package com.example.cashcow_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.weight.WeightSummaryDTO;
import com.example.cashcow_api.models.ECowWeight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CowWeightDAO extends JpaRepository<ECowWeight, Integer>, JpaSpecificationExecutor<ECowWeight> {
    
    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.weight.WeightSummaryDTO(cast(w.createdOn as LocalDate), w.weight) "
            + "FROM com.example.cashcow_api.models.EWeight w "
            + "LEFT JOIN w.cow c "
            + "WHERE w.createdOn > :startDate "
                + "AND w.createdOn < :endDate "
                + "AND c.id = :cowId "
            + "GROUP BY cast(w.createdOn as LocalDate) "
            + "ORDER BY cast(w.createdOn as LocalDate) ASC"
    )
    List<WeightSummaryDTO> findWeightSummary(LocalDateTime startDate, LocalDateTime endDate, Integer cowId);
}
