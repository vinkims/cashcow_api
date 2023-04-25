package com.example.cashcow_api.dtos.cow;

import com.example.cashcow_api.dtos.feed.FeedItemDTO;
import com.example.cashcow_api.models.ECowFeedingFeedItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowFeedingFeedItemDTO {
    
    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "farm", "status"})
    private FeedItemDTO feedItem;

    private Integer feedItemId;

    private Float quantity;

    public CowFeedingFeedItemDTO(ECowFeedingFeedItem cowFeedingFeedItem) {
        setFeedItem(new FeedItemDTO(cowFeedingFeedItem.getFeedItem()));
        setQuantity(cowFeedingFeedItem.getQuantity());
    }
}
