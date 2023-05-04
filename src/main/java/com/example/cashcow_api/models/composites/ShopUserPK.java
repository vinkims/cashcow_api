package com.example.cashcow_api.models.composites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class ShopUserPK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "user_id")
    private Integer userId;

    public ShopUserPK(Integer shopId, Integer userId) {
        setShopId(shopId);
        setUserId(userId);
    }
}
