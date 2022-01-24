package com.example.cashcow_api.dtos.cow;

import com.example.cashcow_api.models.ECow;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CowBasicDTO {
    
    private String category;

    private Integer cowId;
    
    private String name;

    public CowBasicDTO(ECow cow){
        setCategory(cow.getCategory().getName());
        setCowId(cow.getId());
        setName(cow.getName());
    }
}
