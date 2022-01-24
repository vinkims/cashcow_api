package com.example.cashcow_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.cashcow_api.annotations.IsExpenseTypeNameValid;
import com.example.cashcow_api.services.expense.IExpenseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VExpenseTypeName implements ConstraintValidator<IsExpenseTypeNameValid, String> {

    @Autowired
    private IExpenseType sExpenseType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return !sExpenseType.checkExistsByName(value.toUpperCase());
    }
    
}
