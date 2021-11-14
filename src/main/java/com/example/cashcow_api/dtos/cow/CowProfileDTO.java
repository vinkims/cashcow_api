package com.example.cashcow_api.dtos.cow;

import javax.validation.constraints.Pattern;

import com.example.cashcow_api.annotations.IsDateValid;
import com.example.cashcow_api.models.ECowProfile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class CowProfileDTO {

    private String breed;

    private String color;
    
    //@IsDateValid
    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String dateOfBirth;

    //@IsDateValid
    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String dateOfDeath;

    //@IsDateValid
    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String dateOfPurchase;

    //@IsDateValid
    @Pattern(
        regexp = "^(?<year>(?<ya>1|2)(?<yb>[0-9]{3}))-(?<month>(?<ma>0?[1-9])|1[0-2])-(?<day>(?<da>0?[1-9])|(?<db>(1|2)[0-9])|(?<dc>3[0-2]))$",
        message = "date must take the format YYYY-MM-dd"
    )
    private String dateOfSale;

    private String locationBought;

    private Float purchaseAmount;

    private Float saleAmount;

    public CowProfileDTO(ECowProfile profile){
        if (profile.getBreed() != null){
            setBreed(profile.getBreed());
        }
        setColor(profile.getColor());
        if (profile.getDateOfBirth() != null){
            setDateOfBirth(profile.getDateOfBirth().toString());
        }
        if (profile.getDateOfDeath() != null){
            setDateOfDeath(profile.getDateOfDeath().toString());
        }
        if (profile.getDateOfPurchase() != null){
            setDateOfPurchase(profile.getDateOfPurchase().toString());
        }
        if (profile.getDateOfSale() != null){
            setDateOfSale(profile.getDateOfSale().toString());
        }
        if (profile.getLocationBought() != null){
            setLocationBought(profile.getLocationBought());
        }
        if (profile.getPurchaseAmount() != null){
            setPurchaseAmount(profile.getPurchaseAmount());
        }
        if (profile.getSaleAmount() != null){
            setSaleAmount(profile.getSaleAmount());}
    }
}
