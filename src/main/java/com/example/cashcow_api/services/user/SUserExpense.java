package com.example.cashcow_api.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.models.EExpense;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.models.EUserExpense;
import com.example.cashcow_api.repositories.UserExpenseDAO;

@Service
public class SUserExpense implements IUserExpense {

    @Autowired
    private UserExpenseDAO userExpenseDAO;

    @Override
    public EUserExpense create(EUser user, EExpense expense) {

        EUserExpense userExpense = new EUserExpense(user, expense);
        save(userExpense);
        return userExpense;
    }

    @Override
    public void save(EUserExpense userExpense) {
        userExpenseDAO.save(userExpense);
    }
    
}
