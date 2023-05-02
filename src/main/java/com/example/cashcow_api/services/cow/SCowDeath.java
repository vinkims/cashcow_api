package com.example.cashcow_api.services.cow;

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

import com.example.cashcow_api.dtos.cow.CowDeathDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowDeath;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.CowDeathDAO;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SCowDeath implements ICowDeath {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private CowDeathDAO cowDeathDAO;

    @Autowired
    private ICow sCow;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ECowDeath create(CowDeathDTO cowDeathDTO) {
        
        ECowDeath cowDeath = new ECowDeath();
        cowDeath.setCreatedOn(LocalDateTime.now());
        cowDeath.setDescription(cowDeathDTO.getDescription());
        setCow(cowDeath, cowDeathDTO.getCowId());
        Integer statusId = cowDeathDTO.getStatusId() == null ? activeStatusId : cowDeathDTO.getStatusId();
        setStatus(cowDeath, statusId);

        save(cowDeath);
        return cowDeath;
    }

    @Override
    public Optional<ECowDeath> getById(Integer deathId) {
        return cowDeathDAO.findById(deathId);
    }

    @Override
    public ECowDeath getById(Integer deathId, Boolean handleException) {
        Optional<ECowDeath> cowDeath = getById(deathId);
        if (!cowDeath.isPresent() && handleException) {
            throw new NotFoundException("cow death with specified id not found", "cowDeathId");
        }
        return cowDeath.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECowDeath> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECowDeath> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECowDeath>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowDeath");

        Specification<ECowDeath> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cowDeathDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowDeath cowDeath) {
        cowDeathDAO.save(cowDeath);
    }

    public void setCow(ECowDeath cowDeath, Integer cowId) {
        if (cowId == null) { return; }

        ECow cow = sCow.getById(cowId, true);
        cowDeath.setCow(cow);
    }

    public void setStatus(ECowDeath cowDeath, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        cowDeath.setStatus(status);
    }

    @Override
    public ECowDeath update(Integer deathId, CowDeathDTO cowDeathDTO) {

        ECowDeath cowDeath = getById(deathId, true);
        if (cowDeathDTO.getDescription() != null) {
            cowDeath.setDescription(cowDeathDTO.getDescription());
        }
        cowDeath.setUpdatedOn(LocalDateTime.now());
        setCow(cowDeath, cowDeathDTO.getCowId());
        setStatus(cowDeath, cowDeathDTO.getStatusId());

        save(cowDeath);
        return cowDeath;
    }
    
}
