package com.example.cashcow_api.services.cow;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowExpense;
import com.example.cashcow_api.models.EExpense;
import com.example.cashcow_api.repositories.CowExpenseDAO;

public class SCowExpense implements ICowExpense {

    @Autowired
    private CowExpenseDAO cowExpenseDAO;

    @Override
    public ECowExpense create(ECow cow, EExpense expense) {

        ECowExpense cowExpense = new ECowExpense(cow, expense);
        save(cowExpense);
        return cowExpense;
    }

    @Override
    public void save(ECowExpense cowExpense) {
        cowExpenseDAO.save(cowExpense);
    } 
    
}
