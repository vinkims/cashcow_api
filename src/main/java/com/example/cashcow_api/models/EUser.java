package com.example.cashcow_api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Data;

@Entity(name = "users")
@Data
@EntityListeners(value = {AuditingEntityListener.class})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class EUser implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Column(name = "balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EContact> contacts;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", referencedColumnName = "id")
    private EFarm farm;

    @Column(name = "first_name")
    private String firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "passcode")
    private String passcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private ERole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private EShop shop;

    @OneToOne(mappedBy = "user")
    private EShopUser shopUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @OneToOne(mappedBy = "user")
    private EUserMilkPrice userMilkPrice;

    @OneToMany(mappedBy = "user")
    private List<EUserExpense> userExpenses;

    public void setBalance(BigDecimal userBalance){
        balance = balance != null ? balance : new BigDecimal("0");
        if (userBalance != null) {
            balance = balance.add(userBalance);
        }
    }

    public void setPasscode(String pass) {
        if (pass != null) {
            passcode = new BCryptPasswordEncoder().encode(pass);
        }
    }

}
