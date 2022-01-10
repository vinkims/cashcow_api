package com.example.cashcow_api.dtos.expense;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsExpenseTypeNameValid;
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
    
    private String description;

    private Integer id;

    @NotBlank
    @IsExpenseTypeNameValid
    private String name;

    public ExpenseTypeDTO(EExpenseType expenseType){

        if (expenseType.getDescription() != null){
            setDescription(expenseType.getDescription());
        }
        setId(expenseType.getId());
        setName(expenseType.getName());
    }
}
