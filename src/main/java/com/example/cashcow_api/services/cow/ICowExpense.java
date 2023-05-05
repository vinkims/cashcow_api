package com.example.cashcow_api.services.cow;

import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowExpense;
import com.example.cashcow_api.models.EExpense;

public interface ICowExpense {
    
    ECowExpense create(ECow cow, EExpense expense);

    void save(ECowExpense cowExpense);
}
