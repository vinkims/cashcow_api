package com.example.cashcow_api.services.expense;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.expense.ExpenseTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EExpenseType;

public interface IExpenseType {
    
    Boolean checkExistsByName(String name);
    
    EExpenseType create(ExpenseTypeDTO expenseTypeDTO);

    List<EExpenseType> getAll();

    Optional<EExpenseType> getById(Integer expenseTypeId);

    EExpenseType getById(Integer expenseTypeId, Boolean handleException);

    Page<EExpenseType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EExpenseType expenseType);

    EExpenseType update(Integer expenseTypeId, ExpenseTypeDTO expenseTypeDTO);
}
