package com.example.cashcow_api.models;

import java.io.Serializable;
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

import lombok.Data;

@Entity(name = "users")
@Data
@EntityListeners(value = {AuditingEntityListener.class})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class EUser implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Column(name = "balance")
    private Float balance;

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

    @OneToOne(mappedBy = "user")
    private EUserProfile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private ERole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private EShop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    public EUser(){
        setCreatedOn(LocalDateTime.now());
    }

}
