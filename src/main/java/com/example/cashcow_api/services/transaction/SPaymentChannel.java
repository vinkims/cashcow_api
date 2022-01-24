package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.EPaymentChannel;
import com.example.cashcow_api.repositories.PaymentChannelDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SPaymentChannel implements IPaymentChannel {

    @Autowired
    private PaymentChannelDAO paymentChannelDAO;

    @Override
    public Optional<EPaymentChannel> getById(Integer id) {
        return paymentChannelDAO.findById(id);
    }

    @Override
    public List<EPaymentChannel> getList() {
        return paymentChannelDAO.findAll();
    }
    
}
