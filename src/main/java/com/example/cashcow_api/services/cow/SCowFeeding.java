package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.cow.CowFeedingDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.models.ECowFeeding;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.CowFeedingDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.services.user.IUser;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SCowFeeding implements ICowFeeding {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private CowFeedingDAO cowFeedingDAO;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Optional<ECowFeeding> getById(Integer cowFeedingId) {
        return cowFeedingDAO.findById(cowFeedingId);
    }

    @Override
    public ECowFeeding getById(Integer cowFeedingId, Boolean handleException) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Page<ECowFeeding> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaginatedList'");
    }

    @Override
    public void save(ECowFeeding cowFeeding) {
        cowFeedingDAO.save(cowFeeding);
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
        // TODO: Complete this
    }

    @Override
    public ECowFeeding update(Integer cowFeedingId, CowFeedingDTO cowFeedingDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
