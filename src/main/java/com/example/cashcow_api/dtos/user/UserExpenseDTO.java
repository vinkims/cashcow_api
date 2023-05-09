package com.example.cashcow_api.dtos.user;

import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.models.EUserExpense;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserExpenseDTO {
    
    private ExpenseDTO expense;

    private Integer expenseId;

    private Integer userId;

    public UserExpenseDTO(EUserExpense userExpense) {
        setExpense(new ExpenseDTO(userExpense.getExpense()));
    }
}
