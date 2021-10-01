package com.example.cashcow_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.cashcow_api.annotations.IsShopNameValid;
import com.example.cashcow_api.services.shop.IShop;

import org.springframework.beans.factory.annotation.Autowired;

public class VShopName implements ConstraintValidator<IsShopNameValid, String> {

    @Autowired
    private IShop sShop;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        return !sShop.checkExistsByName(value.toUpperCase());
    }
    
}
