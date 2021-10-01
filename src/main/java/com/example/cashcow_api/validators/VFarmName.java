package com.example.cashcow_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.cashcow_api.annotations.IsFarmNameValid;
import com.example.cashcow_api.services.farm.IFarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VFarmName implements ConstraintValidator<IsFarmNameValid, String>{

    @Autowired
    private IFarm sFarm;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        return !sFarm.checkExistsByName(value.toUpperCase());
    }
    
}
