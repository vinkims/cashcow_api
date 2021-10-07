package com.example.cashcow_api.dtos.cow;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.ECowService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class CowServiceDTO {

    private BigDecimal amount;
    
    private CowBasicDTO cow;

    private Integer cowId;

    private String cowServiceType;

    private Integer cowServiceTypeId;

    private LocalDateTime createdOn;

    private Integer id;

    private String results;

    private UserBasicDTO user;

    private Integer userId;

    public CowServiceDTO(ECowService cowService){
        
        setAmount(cowService.getAmount());
        setCow(new CowBasicDTO(cowService.getCow()));
        setCowServiceType(cowService.getCowServiceType().getName());
        setCreatedOn(cowService.getCreatedOn());
        setResults(cowService.getResults());
        setUser(new UserBasicDTO(cowService.getUser()));
    }
}
