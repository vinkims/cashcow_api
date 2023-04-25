package com.example.cashcow_api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.example.cashcow_api.models.composites.CowFeedingFeedItemPK;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cow_feeding_feed_items")
@Data
@NoArgsConstructor
public class ECowFeedingFeedItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CowFeedingFeedItemPK cowFeedingFeedItemPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_feeding_id", referencedColumnName = "id")
    @MapsId(value = "cow_feeding_id")
    private ECowFeeding cowFeeding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_item_id", referencedColumnName = "id")
    @MapsId(value = "feed_item_id")
    private EFeedItem feedItem;

    @Column(name = "quantity")
    private Float quantity;

    public ECowFeedingFeedItem(ECowFeeding cowFeeding, EFeedItem feedItem) {
        setCowFeeding(cowFeeding);
        setFeedItem(feedItem);
        setCowFeedingFeedItemPK(new CowFeedingFeedItemPK(cowFeeding.getId(), feedItem.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        ECowFeedingFeedItem feedingFeedItem = (ECowFeedingFeedItem) o;
        return cowFeeding.getId() == feedingFeedItem.getCowFeeding().getId();
    }

    @Override
    public int hashCode() {
        int hash = 65;
        hash = 31 * hash + cowFeeding.getId().intValue();
        return hash;
    }
}
