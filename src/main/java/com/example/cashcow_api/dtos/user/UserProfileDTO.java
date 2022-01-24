package com.example.cashcow_api.dtos.user;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.cashcow_api.models.EUserProfile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDTO {

    private Float milkPrice;
    
    @Size(min = 4, max = 30)
    private String passcode;

    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String priceExpiresOn;

    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String priceValidOn;

    public UserProfileDTO(EUserProfile userProfile){
        if (userProfile == null){ return; }

        if (userProfile.getMilkPrice() != null){
            this.setMilkPrice(userProfile.getMilkPrice());
        }
        if (userProfile.getPriceExpiresOn() != null){
            this.setPriceExpiresOn(userProfile.getPriceExpiresOn().toString());
        }
        if (userProfile.getPriceValidOn() != null){
            this.setPriceValidOn(userProfile.getPriceValidOn().toString());
        }
    }
}
