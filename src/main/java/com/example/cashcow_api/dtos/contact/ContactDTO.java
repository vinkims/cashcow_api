package com.example.cashcow_api.dtos.contact;

import com.example.cashcow_api.annotations.IsContactValid;
import com.example.cashcow_api.models.EContact;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@IsContactValid
public class ContactDTO {
    
    private Integer contactTypeId;

    private ContactTypeDTO contactType;

    private String contactValue;

    private LocalDateTime createdOn;

    private Integer userId;

    public ContactDTO(EContact contact){
        setContactType(new ContactTypeDTO(contact.getContactType()));
        setContactValue(contact.getValue());
        setCreatedOn(contact.getCreatedOn());
    }
}
