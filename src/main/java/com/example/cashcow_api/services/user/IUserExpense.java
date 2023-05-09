package com.example.cashcow_api.services.user;

import com.example.cashcow_api.models.EExpense;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.models.EUserExpense;

public interface IUserExpense {
    
    EUserExpense create(EUser user, EExpense expense);

    void save(EUserExpense userExpense);
}
