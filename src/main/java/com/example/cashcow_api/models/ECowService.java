package com.example.cashcow_api.models;

import java.io.Serializable;
import java.time.LocalDate;
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

    @Column(name = "amount")
    private Float amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bull_id", referencedColumnName = "id")
    private ECow bull;

    @Column(name = "calving_date")
    private LocalDate calvingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_id", referencedColumnName = "id")
    private ECow cow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_service_type_id", referencedColumnName = "id")
    private ECowServiceType cowServiceType;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "observation_date")
    private LocalDate observationDate;

    @Column(name = "results")
    private String results;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private EUser user;
}
