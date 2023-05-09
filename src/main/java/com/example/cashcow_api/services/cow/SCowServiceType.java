package com.example.cashcow_api.services.cow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowServiceTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECowServiceType;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.CowServiceTypeDAO;
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
public class SCowServiceType implements ICowServiceType {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private CowServiceTypeDAO serviceTypeDAO;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return serviceTypeDAO.existsByName(name);
    }

    @Override
    public ECowServiceType create(CowServiceTypeDTO serviceTypeDTO) {
        
        ECowServiceType serviceType = new ECowServiceType();
        serviceType.setCreatedOn(LocalDateTime.now());
        serviceType.setDescription(serviceTypeDTO.getDescription());
        serviceType.setName(serviceTypeDTO.getName().toUpperCase());
        setFarm(serviceType, serviceTypeDTO.getFarmId());
        Integer statusId = serviceTypeDTO.getStatusId() == null ? activeStatusId : serviceTypeDTO.getStatusId();
        setStatus(serviceType, statusId);

        save(serviceType);

        return serviceType;
    }

    @Override
    public List<ECowServiceType> getAll() {
        return serviceTypeDAO.findAll();
    }

    @Override
    public Optional<ECowServiceType> getById(Integer serviceTypeId) {
        return serviceTypeDAO.findById(serviceTypeId);
    }

    @Override
    public ECowServiceType getById(Integer serviceTypeId, Boolean handleException) {
        Optional<ECowServiceType> serviceType = getById(serviceTypeId);
        if (!serviceType.isPresent() && handleException) {
            throw new NotFoundException("cow service type with specified id not found", "cowServiceTypeId");
        }
        return serviceType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECowServiceType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ECowServiceType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECowServiceType>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowServiceType");

        Specification<ECowServiceType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return serviceTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowServiceType serviceType) {
        serviceTypeDAO.save(serviceType);
    }

    public void setFarm(ECowServiceType serviceType, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        serviceType.setFarm(farm);
    }
    
    public void setStatus(ECowServiceType serviceType, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        serviceType.setStatus(status);
    }

    @Override
    public ECowServiceType update(Integer serviceTypeId, CowServiceTypeDTO serviceTypeDTO) {

        ECowServiceType serviceType = getById(serviceTypeId, true);
        if (serviceTypeDTO.getDescription() != null) {
            serviceType.setDescription(serviceTypeDTO.getDescription());
        }
        if (serviceTypeDTO.getName() != null) {
            serviceType.setName(serviceTypeDTO.getName());
        }
        setFarm(serviceType, serviceTypeDTO.getFarmId());
        setStatus(serviceType, serviceTypeDTO.getStatusId());

        save(serviceType);
        return serviceType;
    }
}
