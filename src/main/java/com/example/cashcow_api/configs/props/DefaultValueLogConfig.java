package com.example.cashcow_api.configs.props;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "logging")
@Data
public class DefaultValueLogConfig {
    
    private Boolean request;

    private Boolean response;

    private List<String> allowedMethods;
}
