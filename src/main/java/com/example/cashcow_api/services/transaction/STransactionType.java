package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.ETransactionType;
import com.example.cashcow_api.repositories.TransactionTypeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class STransactionType implements ITransactionType {

    @Autowired
    private TransactionTypeDAO transactionTypeDAO;

    @Override
    public Optional<ETransactionType> getById(Integer id) {
        return transactionTypeDAO.findById(id);
    }

    @Override
    public List<ETransactionType> getList() {
        return transactionTypeDAO.findAll();
    }
    
}
