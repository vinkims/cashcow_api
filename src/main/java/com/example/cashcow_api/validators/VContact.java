package com.example.cashcow_api.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.cashcow_api.annotations.IsContactValid;
import com.example.cashcow_api.dtos.contact.ContactDTO;
import com.example.cashcow_api.services.contact.SContact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class VContact implements ConstraintValidator<IsContactValid, ContactDTO> {

    @Value(value = "${default.value.contact.email-type-id}")
    private Integer emailTypeId;

    @Value(value = "${default.value.contact.mobile-type-id}")
    private Integer mobileTypeId;

    @Autowired
    private SContact sContact;

    private String defaultMessage;

    @Override
    public void initialize(final IsContactValid isContactValidAnnotation){
        defaultMessage = isContactValidAnnotation.message();
    }

    @Override
    public boolean isValid(ContactDTO contactDTO, ConstraintValidatorContext context) {
        
        Boolean isContactValid;
        String message = "";
        Pattern pattern = Pattern.compile("\\w*");

        context.disableDefaultConstraintViolation();

        if (contactDTO.getContactTypeId().equals(emailTypeId)){
            pattern = Pattern.compile("^(?<a>\\w*)(?<b>[\\.-]\\w*){0,3}@(?<c>\\w+)(?<d>\\.\\w{2,}){1,3}$");
            message = "Invalid email address; value provided must have standard format";
        } else if (contactDTO.getContactTypeId().equals(mobileTypeId)){
            pattern = Pattern.compile("^[0-9]{12}$");
            message = "Invalid mobile number; value must be 12 digits including country code";
        }

        Matcher matcher = pattern.matcher(contactDTO.getContactValue());
        isContactValid = matcher.find();
        message = isContactValid ? defaultMessage : message;

        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        return isContactValid && !sContact.checkExistsByValue(contactDTO.getContactValue().trim());
    }
    
}
