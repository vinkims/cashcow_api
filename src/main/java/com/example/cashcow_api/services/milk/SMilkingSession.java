package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkingSessionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EMilkingSession;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.MilkingSessionDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SMilkingSession implements IMilkingSession {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired 
    private MilkingSessionDAO sessionDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EMilkingSession create(MilkingSessionDTO milkingSessionDTO) {

        EMilkingSession milkingSession = new EMilkingSession();
        milkingSession.setCreatedOn(LocalDateTime.now());
        milkingSession.setDescription(milkingSessionDTO.getDescription());
        milkingSession.setName(milkingSessionDTO.getName());
        setFarm(milkingSession, milkingSessionDTO.getFarmId());
        Integer statusId = milkingSessionDTO.getStatusId() == null 
            ? activeStatusId : milkingSessionDTO.getStatusId();
        setStatus(milkingSession, statusId);

        save(milkingSession);
        return milkingSession;
    }

    @Override
    public List<EMilkingSession> getAll() {
        return sessionDAO.findAll();
    }

    @Override
    public Optional<EMilkingSession> getById(Integer sessionId) {
        return sessionDAO.findById(sessionId);
    }

    @Override
    public EMilkingSession getById(Integer sessionId, Boolean handleException) {
        Optional<EMilkingSession> session = getById(sessionId);
        if (!session.isPresent() && handleException) {
            throw new NotFoundException("milking session with specified id not found", "milkingSessionId");
        }
        return session.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EMilkingSession> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EMilkingSession> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EMilkingSession>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "milkingSession");

        Specification<EMilkingSession> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return sessionDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EMilkingSession milkingSession) {
        sessionDAO.save(milkingSession);
    }

    public void setFarm(EMilkingSession milkingSession, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        milkingSession.setFarm(farm);
    }

    public void setStatus(EMilkingSession milkingSession, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        milkingSession.setStatus(status);
    }

    @Override
    public EMilkingSession update(Integer sessionId, MilkingSessionDTO milkingSessionDTO) {

        EMilkingSession milkingSession = getById(sessionId, true);
        if (milkingSessionDTO.getDescription() != null) {
            milkingSession.setDescription(milkingSessionDTO.getDescription());
        }
        if (milkingSessionDTO.getName() != null) {
            milkingSession.setName(milkingSessionDTO.getName());
        }
        milkingSession.setUpdatedOn(LocalDateTime.now());
        setFarm(milkingSession, milkingSessionDTO.getFarmId());
        setStatus(milkingSession, milkingSessionDTO.getStatusId());

        save(milkingSession);
        return milkingSession;
    }
    
}
