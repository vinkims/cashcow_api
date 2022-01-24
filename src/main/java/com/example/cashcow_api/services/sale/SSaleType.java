package com.example.cashcow_api.services.sale;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.ESaleType;
import com.example.cashcow_api.repositories.SaleTypeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SSaleType implements ISaleType {

    @Autowired
    private SaleTypeDAO saleTypeDAO;

    @Override
    public Optional<ESaleType> getById(Integer id) {
        return saleTypeDAO.findById(id);
    }

    @Override
    public List<ESaleType> getList() {
        return saleTypeDAO.findAll();
    }
    
}
