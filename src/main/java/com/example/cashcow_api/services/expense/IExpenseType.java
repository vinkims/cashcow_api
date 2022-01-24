package com.example.cashcow_api.services.expense;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.expense.ExpenseTypeDTO;
import com.example.cashcow_api.models.EExpenseType;

public interface IExpenseType {
    
    Boolean checkExistsByName(String name);
    
    EExpenseType create(ExpenseTypeDTO expenseTypeDTO);

    List<EExpenseType> getAll();

    Optional<EExpenseType> getById(Integer expenseTypeId);

    void save(EExpenseType expenseType);
}
