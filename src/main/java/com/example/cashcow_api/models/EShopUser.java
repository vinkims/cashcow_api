package com.example.cashcow_api.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.example.cashcow_api.models.composites.ShopUserPK;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "shop_users")
@Data
@NoArgsConstructor
public class EShopUser implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ShopUserPK shopUserPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    @MapsId(value = "shop_id")
    private EShop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId(value = "user_id")
    private EUser user;

    public EShopUser(EShop shop, EUser user) {
        setShop(shop);
        setUser(user);
        setShopUserPK(new ShopUserPK(shop.getId(), user.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        EShopUser shopUser = (EShopUser) o;
        return user.getId() == shopUser.getUser().getId();
    }

    @Override
    public int hashCode() {
        int hash = 65;
        hash = 31 * hash + user.getId().intValue();
        return hash;
    }
}
