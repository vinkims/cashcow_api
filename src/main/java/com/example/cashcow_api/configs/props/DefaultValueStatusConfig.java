package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "status")
public class DefaultValueStatusConfig {

    private Integer activeStatusId;
    
    private Integer preCalvingStatusId;
}
