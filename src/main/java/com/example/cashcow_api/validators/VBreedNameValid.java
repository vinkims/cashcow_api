package com.example.cashcow_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.cashcow_api.annotations.IsBreedNameValid;
import com.example.cashcow_api.services.cow.IBreed;

public class VBreedNameValid implements ConstraintValidator<IsBreedNameValid, String> {
    
    @Autowired
    private IBreed sBreed;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sBreed.checkExistsByName(value);
    }
    
}
