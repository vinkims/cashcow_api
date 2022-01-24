package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "category")
public class DefaultValueCategoryConfig {
    
    private Integer categoryCowId;
}
