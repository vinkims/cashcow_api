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

import com.example.cashcow_api.dtos.cow.CowBreedingDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EBreedingType;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowBreeding;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.CowBreedingDAO;
import com.example.cashcow_api.services.breeding.IBreedingType;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SCowBreeding implements ICowBreeding {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private CowBreedingDAO cowBreedingDAO;

    @Autowired
    private IBreedingType sBreedingType;

    @Autowired
    private ICow sCow;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ECowBreeding create(CowBreedingDTO cowBreedingDTO) {

        ECowBreeding cowBreeding = new ECowBreeding();
        cowBreeding.setBull(getCow(cowBreedingDTO.getBullId()));
        cowBreeding.setCow(getCow(cowBreedingDTO.getCowId()));
        cowBreeding.setCreatedOn(LocalDateTime.now());
        cowBreeding.setDescription(cowBreedingDTO.getDescription());
        setBreedingType(cowBreeding, cowBreedingDTO.getBreedingTypeId());
        Integer statusId = cowBreedingDTO.getStatusId() == null ? activeStatusId : cowBreedingDTO.getStatusId();
        setStatus(cowBreeding, statusId);

        save(cowBreeding);
        return cowBreeding;
    }

    @Override
    public Optional<ECowBreeding> getById(Integer breedingId) {
        return cowBreedingDAO.findById(breedingId);
    }

    @Override
    public ECowBreeding getById(Integer breedingId, Boolean handleException) {
        Optional<ECowBreeding> cowBreeding = getById(breedingId);
        if (!cowBreeding.isPresent() && handleException) {
            throw new NotFoundException("cow breeding with specified id not found", "cowBreedingId");
        }
        return cowBreeding.get();
    }

    public ECow getCow(Integer cowId) {
        if (cowId == null) { return null; }

        ECow cow = sCow.getById(cowId, true);
        return cow;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECowBreeding> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECowBreeding> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECowBreeding>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowBreeding");

        Specification<ECowBreeding> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cowBreedingDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowBreeding cowBreeding) {
        cowBreedingDAO.save(cowBreeding);
    }

    public void setBreedingType(ECowBreeding cowBreeding, Integer breedingTypeId) {
        if (breedingTypeId == null) { return; }

        EBreedingType breedingType = sBreedingType.getById(breedingTypeId, true);
        cowBreeding.setBreedingType(breedingType);
    }

    public void setStatus(ECowBreeding cowBreeding, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        cowBreeding.setStatus(status);
    }

    @Override
    public ECowBreeding update(Integer breedingId, CowBreedingDTO cowBreedingDTO) {

        ECowBreeding cowBreeding = getById(breedingId, true);
        if (cowBreedingDTO.getBullId() != null) {
            cowBreeding.setBull(getCow(cowBreedingDTO.getBullId()));
        }
        if (cowBreedingDTO.getCowId() != null) {
            cowBreeding.setCow(getCow(cowBreedingDTO.getCowId()));
        }
        if (cowBreedingDTO.getDescription() != null) {
            cowBreeding.setDescription(cowBreedingDTO.getDescription());
        }
        cowBreeding.setUpdatedOn(LocalDateTime.now());
        setBreedingType(cowBreeding, cowBreedingDTO.getBreedingTypeId());
        setStatus(cowBreeding, cowBreedingDTO.getStatusId());

        save(cowBreeding);
        return cowBreeding;
    }
    
}
