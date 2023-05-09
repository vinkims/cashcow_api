package com.example.cashcow_api.configs.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "income")
@Data
public class DefaultValueIncomeConfig {
    
    private Integer cowSaleTypeId;

    private Integer milkSaleTypeId;
}
