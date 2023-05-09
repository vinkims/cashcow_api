package com.example.cashcow_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "contacts")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "user"})
public class EContact implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_type_id", referencedColumnName = "id")
    private EContactType contactType;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Id
    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId(value = "userId")
    private EUser user;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * Sets user
     */
    public void setUser(EUser user){
        this.user = user;
        this.userId = user.getId();
    }
}
