package com.example.cashcow_api.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.example.cashcow_api.utils.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "cows")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ECow implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_breed_id", referencedColumnName = "id")
    private ECowBreed breed;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<ECow> calves;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_category_id", referencedColumnName = "id")
    private ECowCategory category;

    @Column(name = "color")
    private String color;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", referencedColumnName = "id")
    private EFarm farm;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "other_details")
    private String otherDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ECow parent;

    @OneToOne(mappedBy = "cow")
    private ECowProfile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    @OneToMany(mappedBy = "cow", fetch = FetchType.LAZY)
    private List<ECowService> services;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    /**
     * Get gender value as string
     */
    public String getGender() {
        return gender == null ? null : gender.getGender();
    }

    /**
     * Sets the corresponding gender value
     */
    public void setGender(String gender) {
        this.gender = Gender.of(gender.toLowerCase());
    }
}
