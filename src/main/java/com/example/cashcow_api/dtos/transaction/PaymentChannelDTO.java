package com.example.cashcow_api.dtos.transaction;

import com.example.cashcow_api.models.EPaymentChannel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class PaymentChannelDTO {
    
    private Integer id;

    private String name;

    private String description;

    public PaymentChannelDTO(EPaymentChannel paymentChannel) {
        setDescription(paymentChannel.getDescription());
        setId(paymentChannel.getId());
        setName(paymentChannel.getName());
    }
}
