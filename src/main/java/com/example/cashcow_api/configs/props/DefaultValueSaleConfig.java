package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "sale")
@Data
public class DefaultValueSaleConfig {
    
    private Integer creditSaleTypeId;
}
