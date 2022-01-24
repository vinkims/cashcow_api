package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.models.EPaymentChannel;

public interface IPaymentChannel {
    
    Optional<EPaymentChannel> getById(Integer id);

    List<EPaymentChannel> getList();
}
