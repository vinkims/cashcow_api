package com.example.cashcow_api.dtos.contact;

import com.example.cashcow_api.models.EContact;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactDTO {
    
    private Integer contactTypeId;

    private String contactValue;

    private String contactType;

    private Integer userId;

    public ContactDTO(EContact contact){
        this.setContactType(contact.getContactType().getName());
        this.setContactValue(contact.getValue());
    }
}
