package com.example.cashcow_api.services.feed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.feed.FeedItemDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EFeedItem;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.FeedItemDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SFeedItem implements IFeedItem {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private FeedItemDAO feedItemDAO;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EFeedItem create(FeedItemDTO feedItemDTO) {

        EFeedItem feedItem = new EFeedItem();
        feedItem.setCreatedOn(LocalDateTime.now());
        feedItem.setMeasurementUnit(feedItemDTO.getMeasurementUnit());
        feedItem.setName(feedItemDTO.getName());
        feedItem.setStock(feedItemDTO.getStock());
        setFarm(feedItem, feedItemDTO.getFarmId());
        Integer statusId = feedItemDTO.getStatusId == null ? activeStatusId : feedItemDTO.getStatusId;
        setStatus(feedItem, statusId);

        save(feedItem);
        return feedItem;
    }

    @Override
    public Optional<EFeedItem> getById(Integer feedItemId) {
        return feedItemDAO.findById(feedItemId);
    }

    @Override
    public EFeedItem getById(Integer feedItemId, Boolean handleException) {
        Optional<EFeedItem> feedItem = getById(feedItemId);
        if (!feedItem.isPresent() && handleException) {
            throw new NotFoundException("feed item with specified id not found", "feedItemId");
        }
        return feedItem.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EFeedItem> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EFeedItem> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EFeedItem>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "feedItem");

        Specification<EFeedItem> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return feedItemDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EFeedItem feedItem) {
        feedItemDAO.save(feedItem);
    }

    public void setFarm(EFeedItem feedItem, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        feedItem.setFarm(farm);
    }

    public void setStatus(EFeedItem feedItem, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        feedItem.setStatus(status);
    }

    @Override
    public EFeedItem update(Integer feedItemId, FeedItemDTO feedItemDTO) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EFeedItem feedItem = getById(feedItemId, true);

        String[] fields = {"MeasurementUnit", "Name", "Stock"};
        for (String field : fields) {
            Method getField = FeedItemDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(feedItemDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EFeedItem.class.getMethod("set" + field, fieldValue.getClass()).invoke(feedItem, fieldValue);
            }
        }

        feedItem.setUpdatedOn(LocalDateTime.now());
        setFarm(feedItem, feedItemDTO.getFarmId());
        setStatus(feedItem, feedItemDTO.getStatusId());

        save(feedItem);
        return feedItem;
    }
    
}
