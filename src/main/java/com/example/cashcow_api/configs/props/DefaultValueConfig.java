package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "default.value")
public class DefaultValueConfig {
    
    private DefaultValueLogConfig logging;

    private DefaultValueUserConfig user;

    private DefaultValueContactConfig contact;

    private DefaultValueRoleConfig role;
}
