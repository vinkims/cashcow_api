package com.example.cashcow_api.services.cow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.cow.CowFeedingDTO;
import com.example.cashcow_api.dtos.cow.CowFeedingFeedItemDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECowFeeding;
import com.example.cashcow_api.models.ECowFeedingFeedItem;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.CowFeedingDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.services.user.IUser;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SCowFeeding implements ICowFeeding {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private CowFeedingDAO cowFeedingDAO;

    @Autowired
    private ICowFeedingFeedItem sCowFeedingFeedItem;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ECowFeeding create(CowFeedingDTO cowFeedingDTO) {
        
        ECowFeeding cowFeeding = new ECowFeeding();
        cowFeeding.setCreatedOn(LocalDateTime.now());
        setFarm(cowFeeding, cowFeedingDTO.getFarmId());
        Integer statusId = cowFeedingDTO.getStatusId() != null ? activeStatusId : cowFeedingDTO.getStatusId();
        setStatus(cowFeeding, statusId);
        setUser(cowFeeding, cowFeedingDTO.getUserId());

        save(cowFeeding);

        setCowFeedingFeedItems(cowFeeding, cowFeedingDTO.getFeedingFeedItems());
        return cowFeeding;
    }

    @Override
    public Optional<ECowFeeding> getById(Integer cowFeedingId) {
        return cowFeedingDAO.findById(cowFeedingId);
    }

    @Override
    public ECowFeeding getById(Integer cowFeedingId, Boolean handleException) {
        Optional<ECowFeeding> cowFeeding = getById(cowFeedingId);
        if (!cowFeeding.isPresent() && handleException) {
            throw new NotFoundException("cow feeding with specified id not found", "cowFeedingId");
        }
        return cowFeeding.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECowFeeding> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECowFeeding> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECowFeeding>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowFeeding");

        Specification<ECowFeeding> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cowFeedingDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowFeeding cowFeeding) {
        cowFeedingDAO.save(cowFeeding);
    }

    public void setCowFeedingFeedItems(ECowFeeding cowFeeding, List<CowFeedingFeedItemDTO> feedingFeedItems) {
        if (feedingFeedItems == null || feedingFeedItems.isEmpty()) { return; }

        List<ECowFeedingFeedItem> cowFeedingFeedItems = new ArrayList<>();
        for (CowFeedingFeedItemDTO itemDTO : feedingFeedItems) {
            ECowFeedingFeedItem cowFeedingFeedItem = sCowFeedingFeedItem.create(cowFeeding, itemDTO);
            cowFeedingFeedItems.add(cowFeedingFeedItem);
        }
        cowFeeding.setFeedingFeedItems(cowFeedingFeedItems);
    }

    public void setFarm(ECowFeeding cowFeeding, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        cowFeeding.setFarm(farm);
    }

    public void setStatus(ECowFeeding cowFeeding, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        cowFeeding.setStatus(status);
    }

    public void setUser(ECowFeeding cowFeeding, Integer userId) {
        if (userId == null) { return; }

        EUser user = sUser.getById(userId, true);
        cowFeeding.setUser(user);
    }

    @Override
    public ECowFeeding update(Integer cowFeedingId, CowFeedingDTO cowFeedingDTO) {

        ECowFeeding cowFeeding = getById(cowFeedingId, true);
        cowFeeding.setUpdatedOn(LocalDateTime.now());
        setFarm(cowFeeding, cowFeedingDTO.getFarmId());
        setStatus(cowFeeding, cowFeedingDTO.getStatusId());
        setUser(cowFeeding, cowFeedingDTO.getUserId());

        save(cowFeeding);

        setCowFeedingFeedItems(cowFeeding, cowFeedingDTO.getFeedingFeedItems());
        return cowFeeding;
    }
    
}
