package com.example.cashcow_api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "cow_services")
public class ECowService implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_id", referencedColumnName = "id")
    private ECow cow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_service_type_id", referencedColumnName = "id")
    private ECowServiceType cowServiceType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "results")
    private String results;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private EUser user;
}
