package com.example.cashcow_api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "user_milk_prices")
@JsonIgnoreProperties(value = {"user"})
@NoArgsConstructor
public class EUserMilkPrice implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
    private EUser user;

    @Column(name = "milk_price")
    private BigDecimal milkPrice;

    @Column(name = "price_expires_on")
    private LocalDate priceExpiresOn;

    @Column(name = "price_valid_on")
    private LocalDate priceValidOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    /**
     * Sets user
     */
    public void setUser(EUser user){
        this.user = user;
        this.userId = user.getId();
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        EUserMilkPrice userMilkPrice = (EUserMilkPrice) o;
        return user.getId() == userMilkPrice.getUser().getId();
    }

    @Override
    public int hashCode(){
        int hash = 65;
        hash = 31 * hash + user.getId().intValue();
        return hash;
    }
}
