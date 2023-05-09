package com.example.cashcow_api.services.feed;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.feed.FeedItemDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EFeedItem;

public interface IFeedItem {
    
    EFeedItem create(FeedItemDTO feedItemDTO);

    Optional<EFeedItem> getById(Integer feedItemId);

    EFeedItem getById(Integer feedItemId, Boolean handleException);

    Page<EFeedItem> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EFeedItem feedItem);

    EFeedItem update(Integer feedItemId, FeedItemDTO feedItemDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
