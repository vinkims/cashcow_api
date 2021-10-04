package com.example.cashcow_api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "cow_profiles")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ECowProfile implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Column(name = "breed")
    private String breed;

    @Column(name = "color")
    private String color;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "cow_id", referencedColumnName = "id")
    private ECow cow;

    @Id
    @Column(name = "cow_id")
    private Integer cowId;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;

    @Column(name = "date_of_purchase")
    private LocalDate dateOfPurchase;

    @Column(name = "date_of_sale")
    private LocalDate dateOfSale;

    @Column(name = "location_bought")
    private String locationBought;

    @Column(name = "purchase_amount")
    private BigDecimal purchaseAmount;

    @Column(name = "sale_amount")
    private BigDecimal saleAmount;

    public void setCow(ECow cow){
        this.cow = cow;
        this.cowId = cow.getId();
    }
}
