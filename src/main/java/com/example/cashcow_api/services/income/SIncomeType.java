package com.example.cashcow_api.services.income;

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

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.income.IncomeTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EIncomeType;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.IncomeTypeDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SIncomeType implements IIncomeType {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IncomeTypeDAO incomeTypeDAO;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EIncomeType create(IncomeTypeDTO incomeTypeDTO) {

        EIncomeType incomeType = new EIncomeType();
        incomeType.setCreatedOn(LocalDateTime.now());
        incomeType.setDescription(incomeTypeDTO.getDescription());
        incomeType.setName(incomeTypeDTO.getName());
        setFarm(incomeType, incomeTypeDTO.getFarmId());
        Integer statusId = incomeTypeDTO.getStatusId() == null ? activeStatusId : incomeTypeDTO.getStatusId();
        setStatus(incomeType, statusId);

        save(incomeType);
        return incomeType;
    }

    @Override
    public Optional<EIncomeType> getById(Integer typeId) {
        return incomeTypeDAO.findById(typeId);
    }

    @Override
    public EIncomeType getById(Integer typeId, Boolean handleException) {
        Optional<EIncomeType> incomeType = getById(typeId);
        if (!incomeType.isPresent() && handleException) {
            throw new NotFoundException("income type with specified id not found", "incomeTypeId");
        }
        return incomeType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EIncomeType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EIncomeType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EIncomeType>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "incomeType");

        Specification<EIncomeType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return incomeTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EIncomeType incomeType) {
        incomeTypeDAO.save(incomeType);
    }

    public void setFarm(EIncomeType incomeType, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        incomeType.setFarm(farm);
    }

    public void setStatus(EIncomeType incomeType, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        incomeType.setStatus(status);
    }

    @Override
    public EIncomeType update(Integer typeId, IncomeTypeDTO incomeTypeDTO) {

        EIncomeType incomeType = getById(typeId, true);
        if (incomeTypeDTO.getDescription() != null) {
            incomeType.setDescription(incomeTypeDTO.getDescription());
        }
        if (incomeTypeDTO.getName() != null) {
            incomeType.setName(incomeTypeDTO.getName());
        }
        incomeType.setUpdatedOn(LocalDateTime.now());
        setFarm(incomeType, incomeTypeDTO.getFarmId());
        setStatus(incomeType, incomeTypeDTO.getStatusId());

        save(incomeType);
        return incomeType;
    }
    
}
