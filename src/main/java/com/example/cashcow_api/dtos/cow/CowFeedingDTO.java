package com.example.cashcow_api.dtos.cow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.ECowFeeding;
import com.example.cashcow_api.models.ECowFeedingFeedItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CowFeedingDTO {
    
    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    private List<CowFeedingFeedItemDTO> feedingFeedItems;

    private UserBasicDTO user;

    private Integer userId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public CowFeedingDTO(ECowFeeding cowFeeding) {
        setCowFeedingFeedItems(cowFeeding.getFeedingFeedItems());
        setCreatedOn(cowFeeding.getCreatedOn());
        setFarm(new FarmDTO(cowFeeding.getFarm()));
        setId(cowFeeding.getId());
        setStatus(new StatusDTO(cowFeeding.getStatus()));
        setUpdatedOn(cowFeeding.getUpdatedOn());
        setUser(new UserBasicDTO(cowFeeding.getUser()));
    }

    public void setCowFeedingFeedItems(List<ECowFeedingFeedItem> cowFeedingFeedItems) {
        if (cowFeedingFeedItems == null || cowFeedingFeedItems.isEmpty()) { return; }

        feedingFeedItems = new ArrayList<>();
        for (ECowFeedingFeedItem item : cowFeedingFeedItems) {
            feedingFeedItems.add(new CowFeedingFeedItemDTO(item));
        }
    }
}
