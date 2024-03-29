package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "milk")
@Data
public class DefaultValueMilkConfig {
    
    private Float pricePerLitre;
}
