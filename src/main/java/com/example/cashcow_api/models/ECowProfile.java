package com.example.cashcow_api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "cow_profiles")
public class ECowProfile implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cow_id")
    private Integer cowId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "cow_id", referencedColumnName = "id")
    private ECow cow;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "date_of_purchase")
    private Date dateOfPurchase;

    @Column(name = "date_of_death")
    private Date dateOfDeath;

    @Column(name = "date_of_sale")
    private Date dateOfSale;

    @Column(name = "purchase_amount")
    private BigDecimal purchaseAmount;

    @Column(name = "sale_amount")
    private BigDecimal saleAmount;

    @Column(name = "location_bought")
    private String locationBought;
}
