package com.example.cashcow_api.services.expense;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EExpense;

import org.springframework.data.domain.Page;

public interface IExpense {
    
    EExpense create(ExpenseDTO expenseDTO);

    Optional<EExpense> getById(Integer id);

    EExpense getById(Integer id, Boolean handleException);

    Page<EExpense> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EExpense expense);

    EExpense update(Integer id, ExpenseDTO expenseDTO);
}
