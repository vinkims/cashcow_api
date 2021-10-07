package com.example.cashcow_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.cashcow_api.annotations.IsCowServiceTypeNameValid;
import com.example.cashcow_api.services.cow.ICowServiceType;

import org.springframework.beans.factory.annotation.Autowired;

public class VCowServiceTypeName implements ConstraintValidator<IsCowServiceTypeNameValid, String> {

    @Autowired
    private ICowServiceType sCowServiceType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        return !sCowServiceType.checkExistsByName(value.toUpperCase());
    }
    
}
