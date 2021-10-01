package com.example.cashcow_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.cashcow_api.annotations.IsCowNameValid;
import com.example.cashcow_api.services.cow.ICow;

import org.springframework.beans.factory.annotation.Autowired;

public class VCowName implements ConstraintValidator<IsCowNameValid, String>{

    @Autowired
    private ICow sCow;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        return !sCow.checkExistsByName(value.toUpperCase());
    }
    
}
