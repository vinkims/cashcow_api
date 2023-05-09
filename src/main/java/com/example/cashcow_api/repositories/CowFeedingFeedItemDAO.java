package com.example.cashcow_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cashcow_api.models.ECowFeedingFeedItem;
import com.example.cashcow_api.models.composites.CowFeedingFeedItemPK;

public interface CowFeedingFeedItemDAO extends JpaRepository<ECowFeedingFeedItem, CowFeedingFeedItemPK> {
    
}
