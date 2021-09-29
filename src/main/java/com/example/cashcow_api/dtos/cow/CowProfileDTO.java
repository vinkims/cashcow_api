package com.example.cashcow_api.dtos.cow;

import java.math.BigDecimal;

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
    
    private String dateOfBirth;

    private String dateOfDeath;

    private String dateOfPurchase;

    private String dateOfSale;

    private String locationBought;

    private BigDecimal purchaseAmount;

    private BigDecimal saleAmount;

    public CowProfileDTO(ECowProfile profile){
        setBreed(profile.getBreed());
        setDateOfBirth(profile.getDateOfBirth().toString());
        setDateOfDeath(profile.getDateOfDeath().toString());
        setDateOfPurchase(profile.getDateOfPurchase().toString());
        setDateOfSale(profile.getDateOfSale().toString());
        setLocationBought(profile.getLocationBought());
        setPurchaseAmount(profile.getPurchaseAmount());
        setSaleAmount(profile.getSaleAmount());
    }
}
