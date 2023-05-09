package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.PaymentChannelDTO;
import com.example.cashcow_api.models.EPaymentChannel;

public interface IPaymentChannel {

    EPaymentChannel create(PaymentChannelDTO paymentChannelDTO);
    
    Optional<EPaymentChannel> getById(Integer id);

    EPaymentChannel getById(Integer id, Boolean handleException);

    List<EPaymentChannel> getList();

    Page<EPaymentChannel> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EPaymentChannel paymentChannel);

    EPaymentChannel update(Integer id, PaymentChannelDTO paymentChannelDTO);
}
