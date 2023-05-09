package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cashcow_api.dtos.feed.FeedItemDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.EFeedItem;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.feed.IFeedItem;

@RestController
@RequestMapping(path = "/feed/item")
public class CFeedItem {
    
    @Autowired
    private IFeedItem sFeedItem;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createFeedItem(@RequestBody FeedItemDTO feedItemDTO) throws URISyntaxException {

        EFeedItem feedItem = sFeedItem.create(feedItemDTO);

        return ResponseEntity
            .created(new URI("/" + feedItem.getId()))
            .body(new SuccessResponse(201, "successfully created feed item", new FeedItemDTO(feedItem)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList(
            "createdOn", "updatedOn", "farm.id", "stock", "measurementUnit", "status.id"));

        Page<EFeedItem> feedItems = sFeedItem.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched feed items", feedItems, 
                FeedItemDTO.class, EFeedItem.class));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getFeedItem(@PathVariable Integer id) {

        EFeedItem feedItem = sFeedItem.getById(id, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched feed item", new FeedItemDTO(feedItem)));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateFeedItem(@PathVariable Integer id, @RequestBody FeedItemDTO feedItemDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EFeedItem feedItem = sFeedItem.update(id, feedItemDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated feed item", new FeedItemDTO(feedItem)));
    }
}
