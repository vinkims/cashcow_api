package com.example.cashcow_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.example.cashcow_api.dtos.milk.CustomerSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.CustomerSaleTotalDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO;
import com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO;
import com.example.cashcow_api.models.EMilkSale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MilkSaleDAO extends JpaRepository<EMilkSale, Integer>, JpaSpecificationExecutor<EMilkSale>{

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.CustomerSaleSummaryDTO(ms.amount, cast(ms.createdOn as LocalDate), ms.quantity) "
            + "FROM com.example.cashcow_api.models.EMilkSale ms "
            + "LEFT JOIN ms.customer c "
            + "WHERE ms.createdOn > :startDate "
                + "AND ms.createdOn < :endDate "
                + "AND c.id = :customerId "
            + "ORDER BY cast(ms.createdOn as LocalDate)"
    )
    List<CustomerSaleSummaryDTO> findCustomerSaleSummary(LocalDateTime startDate, LocalDateTime endDate, Integer customerId);

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.CustomerSaleTotalDTO(sum(ms.amount), sum(ms.quantity)) "
            + "FROM com.example.cashcow_api.models.EMilkSale ms "
            + "LEFT JOIN ms.customer c "
            + "WHERE ms.createdOn > :startDate "
                + "AND ms.createdOn < :endDate "
                + "AND c.id = :customerId"
    )
    List<CustomerSaleTotalDTO> findCustomerSaleTotal(LocalDateTime startDate, LocalDateTime endDate, Integer customerId);

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO(sum(ms.amount), cast(ms.createdOn as LocalDate), sum(ms.quantity), s.name) "
            + "FROM com.example.cashcow_api.models.EMilkSale ms "
            + "LEFT JOIN ms.shop s "
            + "WHERE ms.createdOn > :startDate "
                + "AND ms.createdOn < :endDate "
            + "GROUP BY cast(ms.createdOn as LocalDate), s.name"
    )
    List<MilkSaleSummaryDTO> findMilkSaleSummary(LocalDateTime startDate, LocalDateTime endDate);

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.MilkSaleSummaryDTO(sum(ms.amount), cast(ms.createdOn as LocalDate), sum(ms.quantity), s.name) "
            + "FROM com.example.cashcow_api.models.EMilkSale ms "
            + "LEFT JOIN ms.shop s "
            + "WHERE ms.createdOn > :startDate "
                + "AND ms.createdOn < :endDate "
                + "AND s.id = :shopId "
            + "GROUP BY s.name, cast(ms.createdOn as LocalDate)"
    )
    List<MilkSaleSummaryDTO> findMilkSaleSummaryByShop(LocalDateTime startDate, LocalDateTime endDate, Integer shopId);

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.milk.MilkSaleTotalDTO(sum(ms.amount), cast(ms.createdOn as LocalDate), sum(ms.quantity)) "
            + "FROM com.example.cashcow_api.models.EMilkSale ms "
            + "WHERE ms.createdOn > :startDate "
                + "AND ms.createdOn < :endDate "
            + "GROUP BY cast(ms.createdOn as LocalDate) "
            + "ORDER BY cast(ms.createdOn as LocalDate) ASC"
    )
    List<MilkSaleTotalDTO> findMilkSaleTotal(LocalDateTime startDate, LocalDateTime endDate);
}
