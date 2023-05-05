package com.example.cashcow_api.dtos.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EIncome;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class IncomeDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "farm", "description", "status"})
    private IncomeTypeDTO incomeType;

    private Integer incomeTypeId;

    private BigDecimal amount;

    private String reference;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public IncomeDTO(EIncome income) {
        setAmount(income.getAmount());
        setCreatedOn(income.getCreatedOn());
        setFarm(new FarmDTO(income.getFarm()));
        setId(income.getId());
        setIncomeType(new IncomeTypeDTO(income.getIncomeType()));
        setReference(income.getReference());
        setStatus(new StatusDTO(income.getStatus()));
        setUpdatedOn(income.getUpdatedOn());
    }
}
