package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;

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

    private Float amount;

    private CowBasicDTO bull;

    private Integer bullId;

    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String calvingDate;
    
    private CowBasicDTO cow;

    private Integer cowId;

    private String cowServiceType;

    private Integer cowServiceTypeId;

    private LocalDateTime createdOn;

    private Integer id;

    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String observationDate;

    private String results;

    private UserBasicDTO user;

    private Integer userId;

    public CowServiceDTO(ECowService cowService){
        if (cowService.getAmount() != null){
            setAmount(cowService.getAmount());
        }
        if (cowService.getBull() != null){
            setBull(new CowBasicDTO(cowService.getBull()));
        }
        if (cowService.getCalvingDate() != null){
            setCalvingDate(cowService.getCalvingDate().toString());
        }
        setCow(new CowBasicDTO(cowService.getCow()));
        setCowServiceType(cowService.getCowServiceType().getName());
        setCreatedOn(cowService.getCreatedOn());
        if (cowService.getObservationDate() != null){
            setObservationDate(cowService.getObservationDate().toString());
        }
        if (cowService.getResults() != null){
            setResults(cowService.getResults());
        }
        if (cowService.getUser() != null){
            setUser(new UserBasicDTO(cowService.getUser()));
        }
    }
}
