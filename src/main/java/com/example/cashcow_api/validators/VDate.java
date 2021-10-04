package com.example.cashcow_api.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.cashcow_api.annotations.IsDateValid;

public class VDate implements ConstraintValidator<IsDateValid, String> {

    private String defaultMessage;

    @Override
    public void initialize(final IsDateValid isDateValidAnnotation){
        defaultMessage = isDateValidAnnotation.message();
    }

    @Override
    public boolean isValid(String dateValue, ConstraintValidatorContext context) {
        
        Boolean isDateValid;
        String message = "";
        Pattern pattern = Pattern.compile("\\w*");

        context.disableDefaultConstraintViolation();

        pattern = Pattern.compile("^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$");
        message = "Invalid date; date value must be in the format YYYY-MM-dd";

        Matcher matcher = pattern.matcher(dateValue);
        isDateValid = matcher.find();
        message = isDateValid ? defaultMessage : message;

        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        return isDateValid;
    }
    
}
