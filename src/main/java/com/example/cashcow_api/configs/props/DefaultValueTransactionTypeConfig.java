package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "transaction-type")
public class DefaultValueTransactionTypeConfig {
    
    private Integer creditTransactiontypeId;

    private Integer milkSaleTransactionTypeId;
}
