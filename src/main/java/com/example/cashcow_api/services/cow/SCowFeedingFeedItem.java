package com.example.cashcow_api.services.cow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.cow.CowFeedingFeedItemDTO;
import com.example.cashcow_api.models.ECowFeeding;
import com.example.cashcow_api.models.ECowFeedingFeedItem;
import com.example.cashcow_api.models.EFeedItem;
import com.example.cashcow_api.repositories.CowFeedingFeedItemDAO;
import com.example.cashcow_api.services.feed.IFeedItem;

@Service
public class SCowFeedingFeedItem implements ICowFeedingFeedItem {

    @Autowired
    private CowFeedingFeedItemDAO cowFeedingFeedItemDAO;

    @Autowired
    private IFeedItem sFeedItem;

    @Override
    public ECowFeedingFeedItem create(ECowFeeding cowFeeding, CowFeedingFeedItemDTO feedingFeedItemDTO) {
        
        ECowFeedingFeedItem cowFeedingFeedItem = new ECowFeedingFeedItem(cowFeeding, null);
        cowFeedingFeedItem.setQuantity(feedingFeedItemDTO.getQuantity());
        setFeedItem(cowFeedingFeedItem, feedingFeedItemDTO.getFeedItemId());

        save(cowFeedingFeedItem);
        return cowFeedingFeedItem;
    }

    @Override
    public void save(ECowFeedingFeedItem cowFeedingFeedItem) {
        cowFeedingFeedItemDAO.save(cowFeedingFeedItem);
    }

    public void setFeedItem(ECowFeedingFeedItem cowFeedingFeedItem, Integer feedItemId) {
        if (feedItemId == null) { return; }

        EFeedItem feedItem = sFeedItem.getById(feedItemId, true);
        cowFeedingFeedItem.setFeedItem(feedItem);
    }
    
}
