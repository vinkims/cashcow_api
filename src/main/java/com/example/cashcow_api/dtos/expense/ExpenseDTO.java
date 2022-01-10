package com.example.cashcow_api.dtos.expense;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.cow.CowBasicDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
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
    
    private Float amount;

    private CowBasicDTO cow;

    private Integer cowId;

    private LocalDateTime createdOn;

    private String description;

    private ExpenseTypeDTO expenseType;

    private Integer expenseTypeId;

    private Integer id;

    private String status;

    private Integer statusId;

    private UserBasicDTO user;

    private Integer userId;

    public ExpenseDTO(EExpense expense){
        setAmount(expense.getAmount());
        if (expense.getCow() != null){
            setCow(new CowBasicDTO(expense.getCow()));
        }
        setCreatedOn(expense.getCreatedOn());
        if (expense.getDescription() != null){
            setDescription(expense.getDescription());
        }
        setExpenseType(new ExpenseTypeDTO(expense.getExpenseType()));
        setId(expense.getId());
        setStatus(expense.getStatus().getName());
        setUser(new UserBasicDTO(expense.getUser()));
    }
}
