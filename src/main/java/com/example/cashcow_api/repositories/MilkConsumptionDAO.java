package com.example.cashcow_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.milk.MilkConsumptionSummaryDTO;
import com.example.cashcow_api.models.EMilkConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MilkConsumptionDAO extends JpaRepository<EMilkConsumption, Integer>, JpaSpecificationExecutor<EMilkConsumption>{
    
    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.MilkConsumptionSummaryDTO(c.unitCost, cc.name, cast(c.createdOn as LocalDate), c.quantity, s.name, (c.unitCost * c.quantity) ) "
            + "FROM com.example.cashcow_api.models.EMilkConsumption c "
            + "LEFT JOIN c.category cc "
            + "LEFT JOIN c.session s "
            + "WHERE c.createdOn > :startDate "
                + "AND c.createdOn < :endDate "
            + "GROUP BY cast(c.createdOn as LocalDate), cc.name, s.name, c.unitCost, c.quantity "
            + "ORDER BY cast(c.createdOn as LocalDate) ASC"
    )
    List<MilkConsumptionSummaryDTO> findMilkConsumtionSummary(LocalDateTime startDate, LocalDateTime endDate);
}
