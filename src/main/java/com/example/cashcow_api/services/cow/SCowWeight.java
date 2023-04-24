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

import com.example.cashcow_api.dtos.cow.CowWeightDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowWeight;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.CowWeightDAO;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SCowWeight implements ICowWeight {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private CowWeightDAO cowWeightDAO;

    @Autowired
    private ICow sCow;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ECowWeight create(CowWeightDTO cowWeightDTO) {
        
        ECowWeight cowWeight = new ECowWeight();
        cowWeight.setCreatedOn(LocalDateTime.now());
        cowWeight.setWeight(cowWeightDTO.getWeight());
        setCow(cowWeight, cowWeightDTO.getCowId());
        Integer statusId = cowWeightDTO.getStatusId() == null ? activeStatusId : cowWeightDTO.getStatusId();
        setStatus(cowWeight, statusId);

        save(cowWeight);
        return cowWeight;
    }

    @Override
    public Optional<ECowWeight> getById(Integer cowWeightId) {
        return cowWeightDAO.findById(cowWeightId);
    }

    @Override
    public ECowWeight getById(Integer cowWeightId, Boolean handleException) {
        Optional<ECowWeight> cowWeight = getById(cowWeightId);
        if (!cowWeight.isPresent() && handleException) {
            throw new NotFoundException("cow weight with specified id not found", "cowWeightId");
        }
        return cowWeight.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECowWeight> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECowWeight> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECowWeight>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowWeight");

        Specification<ECowWeight> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cowWeightDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowWeight cowWeight) {
        cowWeightDAO.save(cowWeight);
    }

    public void setCow(ECowWeight cowWeight, Integer cowId) {
        if (cowId == null) { return; }

        ECow cow = sCow.getById(cowId, true);
        cowWeight.setCow(cow);
    }

    public void setStatus(ECowWeight cowWeight, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        cowWeight.setStatus(status);
    }

    @Override
    public ECowWeight update(Integer cowWeightId, CowWeightDTO cowWeightDTO) {

        ECowWeight cowWeight = getById(cowWeightId, true);
        cowWeight.setUpdatedOn(LocalDateTime.now());
        if (cowWeightDTO.getWeight() != null) {
            cowWeight.setWeight(cowWeightDTO.getWeight());
        }
        setCow(cowWeight, cowWeightDTO.getCowId());
        setStatus(cowWeight, cowWeightDTO.getStatusId());

        save(cowWeight);
        return cowWeight;
    }
    
}
