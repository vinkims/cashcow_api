package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "default.value")
public class DefaultValueConfig {
    
    private DefaultValueCategoryConfig category;

    private DefaultValueContactConfig contact;
    
    private DefaultValueLogConfig logging;

    private DefaultValueRoleConfig role;

    private DefaultValueSecurityConfig security;
    
    private DefaultValueStatusConfig status;

    private DefaultValueTransactionTypeConfig transactionType;
    
    private DefaultValueUserConfig user;
}
