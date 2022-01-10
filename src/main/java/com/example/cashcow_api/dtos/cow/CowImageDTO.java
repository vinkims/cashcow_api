package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;

import com.example.cashcow_api.models.ECowImage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class CowImageDTO {
    
    private LocalDateTime createdOn;

    private Integer id;

    private byte[] image;

    public CowImageDTO(ECowImage cowImage){
        setCreatedOn(cowImage.getCreatedOn());
        setId(cowImage.getId());
        setImage(cowImage.getImage());
    }
}
