package com.example.cashcow_api.dtos.feed;

import java.time.LocalDateTime;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.EFeedItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class FeedItemDTO {
    
    private Integer id;

    private String name;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private Float stock;

    private String measurementUnit;

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
    private FarmDTO farm;

    private Integer farmId;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    public FeedItemDTO(EFeedItem feedItem) {
        setCreatedOn(feedItem.getCreatedOn());
        setFarm(new FarmDTO(feedItem.getFarm()));
        setId(feedItem.getId());
        setMeasurementUnit(feedItem.getMeasurementUnit());
        setName(feedItem.getName());
        setStatus(new StatusDTO(feedItem.getStatus()));
        setStock(feedItem.getStock());
        setUpdatedOn(feedItem.getUpdatedOn());
    }
}
