package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "transaction-type")
public class DefaultValueTransactionTypeConfig {

    private Integer cowPurchaseTypeId;

    private Integer cowSaleTypeId;

    private Integer cowServiceTypeId;
    
    private Integer creditTransactiontypeId;

    private Integer milkSaleTransactionTypeId;

    private Integer productPaymentTypeId;

    private Integer staffAdvanceTypeId;

    private Integer staffSalaryTypeId;

    private Integer transportTypeId;
}
