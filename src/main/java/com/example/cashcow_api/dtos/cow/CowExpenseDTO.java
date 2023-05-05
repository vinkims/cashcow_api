package com.example.cashcow_api.dtos.cow;

import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.models.ECowExpense;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowExpenseDTO {
    
    private ExpenseDTO expense;

    private Integer expenseId;

    private Integer cowId;

    public CowExpenseDTO(ECowExpense cowExpense) {
        setExpense(new ExpenseDTO(cowExpense.getExpense()));
    }
}
