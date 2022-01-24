package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "contact")
@Data
public class DefaultValueContactConfig {
    
    private Integer emailTypeId;

    private Integer mobileTypeId;
}
