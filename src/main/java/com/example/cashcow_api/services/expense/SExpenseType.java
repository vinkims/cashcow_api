package com.example.cashcow_api.services.expense;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.expense.ExpenseTypeDTO;
import com.example.cashcow_api.models.EExpenseType;
import com.example.cashcow_api.repositories.ExpenseTypeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SExpenseType implements IExpenseType {

    @Autowired
    private ExpenseTypeDAO expenseTypeDAO;

    @Override
    public Boolean checkExistsByName(String name) {
        return expenseTypeDAO.existsByName(name);
    }

    @Override
    public EExpenseType create(ExpenseTypeDTO expenseTypeDTO) {
        
        EExpenseType expenseType = new EExpenseType();
        if (expenseTypeDTO.getDescription() != null){
            expenseType.setDescription(expenseTypeDTO.getDescription());
        }
        expenseType.setName(expenseTypeDTO.getName());
        save(expenseType);
        
        return expenseType;
    }

    @Override
    public List<EExpenseType> getAll() {
        return expenseTypeDAO.findAll();
    }

    @Override
    public Optional<EExpenseType> getById(Integer expenseTypeId) {
        return expenseTypeDAO.findById(expenseTypeId);
    }

    @Override
    public void save(EExpenseType expenseType) {
        expenseTypeDAO.save(expenseType);
    }
    
}
