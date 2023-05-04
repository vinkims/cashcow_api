package com.example.cashcow_api.dtos.expense;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsExpenseTypeNameValid;
import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EExpenseType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class ExpenseTypeDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @NotBlank
    @IsExpenseTypeNameValid
    private String name;

    private String description;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public ExpenseTypeDTO(EExpenseType expenseType){
        setCreatedOn(expenseType.getCreatedOn());
        setDescription(expenseType.getDescription());
        if (expenseType.getFarm() != null) {
            setFarm(new FarmDTO(expenseType.getFarm()));
        }
        setId(expenseType.getId());
        setName(expenseType.getName());
        if (expenseType.getStatus() != null) {
            setStatus(new StatusDTO(expenseType.getStatus()));
        }
        setUpdatedOn(expenseType.getUpdatedOn());
    }
}
