package com.example.cashcow_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "role")
@Data
public class DefaultValueRoleConfig {

    private Integer adminRoleId;

    private Integer farmAttendantRoleId;

    private Integer shopAttendantRoleId;
    
    private Integer systemAdminRoleId;
}
