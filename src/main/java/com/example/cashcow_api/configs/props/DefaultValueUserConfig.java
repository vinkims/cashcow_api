package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "user")
@Data
public class DefaultValueUserConfig {
    
    private String adminPassword;

    private String adminEmail;

    private String userApiClientName;
}
