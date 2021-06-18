package com.example.cashcow_api.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "cows")
public class ECow implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "breed")
    private String breed;

    @OneToMany(mappedBy = "calf", fetch = FetchType.LAZY)
    private List<ECow> calves;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calf_id", referencedColumnName = "id")
    private ECow calf;

    @OneToOne(mappedBy = "cow")
    private ECowProfile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_category_id", referencedColumnName = "id")
    private ECowCategory category;
}
