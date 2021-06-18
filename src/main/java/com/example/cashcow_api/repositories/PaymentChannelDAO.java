package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EPaymentChannel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentChannelDAO extends JpaRepository<EPaymentChannel, Integer>{
    
}
