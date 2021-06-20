package com.example.cashcow_api.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "user_profiles")
@JsonIgnoreProperties(value = {"user"})
@NoArgsConstructor
public class EUserProfile implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
    private EUser user;

    @Column(name = "passcode")
    private String passcode;

    public void setPasscode(String passcode){
        if (passcode != null){
            this.passcode = new BCryptPasswordEncoder().encode(passcode);
        }
    }

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
        EUserProfile userProfile = (EUserProfile) o;
        return user.getId() == userProfile.getUser().getId();
    }

    @Override
    public int hashCode(){
        int hash = 65;
        hash = 31 * hash + user.getId().intValue();
        return hash;
    }
}
