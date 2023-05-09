package com.example.cashcow_api.dtos.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EExpense;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDTO {
    
    private Integer id;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "description", "farm", "status"})
    private ExpenseTypeDTO expenseType;

    private Integer expenseTypeId;
    
    private BigDecimal amount;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private Integer cowId;

    private Integer userId;

    private String description;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public ExpenseDTO(EExpense expense){
        setAmount(expense.getAmount());
        setCreatedOn(expense.getCreatedOn());
        setDescription(expense.getDescription());
        setExpenseType(new ExpenseTypeDTO(expense.getExpenseType()));
        setId(expense.getId());
        setStatus(new StatusDTO(expense.getStatus()));
    }
}
