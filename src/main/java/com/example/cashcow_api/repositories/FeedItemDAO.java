package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.cashcow_api.models.EFeedItem;

public interface FeedItemDAO extends JpaRepository<EFeedItem, Integer>, JpaSpecificationExecutor<EFeedItem> {
    
}
