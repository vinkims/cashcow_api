package com.example.cashcow_api.dtos.cow;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;

import com.example.cashcow_api.dtos.status.StatusDTO;
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

    private Integer id;

    private BigDecimal cost;
    
    private CowBasicDTO cow;

    private Integer cowId;

    @JsonIgnoreProperties(value = {"createdOn", "farm", "status"})
    private CowServiceTypeDTO cowServiceType;

    private Integer cowServiceTypeId;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String observationDate;

    private String remarks;

    private UserBasicDTO user;

    private Integer userId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public CowServiceDTO(ECowService cowService){
        setCost(cowService.getCost());
        setCow(new CowBasicDTO(cowService.getCow()));
        setCowServiceType(new CowServiceTypeDTO(cowService.getCowServiceType()));
        setCreatedOn(cowService.getCreatedOn());
        setRemarks(cowService.getRemarks());
        if (cowService.getStatus() != null) {
            setStatus(new StatusDTO(cowService.getStatus()));
        }
        if (cowService.getUser() != null){
            setUser(new UserBasicDTO(cowService.getUser()));
        }
        setUpdatedOn(cowService.getUpdatedOn());
    }
}
