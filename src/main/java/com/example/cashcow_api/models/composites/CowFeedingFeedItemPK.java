package com.example.cashcow_api.models.composites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class CowFeedingFeedItemPK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "cow_feeding_id")
    private Integer cowFeedingId;

    @Column(name = "feed_item_id")
    private Integer feedItemId;

    public CowFeedingFeedItemPK(Integer cowFeedingId, Integer feedItemId) {
        setCowFeedingId(cowFeedingId);
        setFeedItemId(feedItemId);
    }
}
