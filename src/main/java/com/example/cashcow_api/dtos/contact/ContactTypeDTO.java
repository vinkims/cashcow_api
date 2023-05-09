package com.example.cashcow_api.dtos.contact;

import com.example.cashcow_api.models.EContactType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ContactTypeDTO {
    
    private Integer id;

    private String name;

    private String description;

    private String regexValue;

    public ContactTypeDTO(EContactType contactType) {
        setDescription(contactType.getDescription());
        setId(contactType.getId());
        setName(contactType.getName());
        setRegexValue(contactType.getRegexValue());
    }
}
