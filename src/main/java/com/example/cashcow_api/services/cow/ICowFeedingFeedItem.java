package com.example.cashcow_api.services.cow;

import com.example.cashcow_api.dtos.cow.CowFeedingFeedItemDTO;
import com.example.cashcow_api.models.ECowFeeding;
import com.example.cashcow_api.models.ECowFeedingFeedItem;

public interface ICowFeedingFeedItem {
    
    ECowFeedingFeedItem create(ECowFeeding cowFeeding, CowFeedingFeedItemDTO feedingFeedItemDTO);

    void save(ECowFeedingFeedItem cowFeedingFeedItem);
}
