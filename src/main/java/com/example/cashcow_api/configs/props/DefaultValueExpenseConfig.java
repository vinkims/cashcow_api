package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "expense")
@Data
public class DefaultValueExpenseConfig {
    
    private Integer cowPurchaseTypeId;

    private Integer productPurchaseTypeId;

    private Integer serviceExpenseTypeId;

    private Integer transportTypeId;
}
