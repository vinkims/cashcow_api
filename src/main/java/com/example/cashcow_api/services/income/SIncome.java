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
import com.example.cashcow_api.dtos.income.IncomeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EIncome;
import com.example.cashcow_api.models.EIncomeType;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.IncomeDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SIncome implements IIncome {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IIncomeType sIncomeType;

    @Autowired
    private IncomeDAO incomeDAO;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EIncome create(IncomeDTO incomeDTO) {

        EIncome income = new EIncome();
        income.setAmount(incomeDTO.getAmount());
        income.setCreatedOn(LocalDateTime.now());
        income.setReference(incomeDTO.getReference());
        setFarm(income, incomeDTO.getFarmId());
        setIncomeType(income, incomeDTO.getIncomeTypeId());
        Integer statusId = incomeDTO.getStatusId() == null ? completeStatusId : incomeDTO.getStatusId();
        setStatus(income, statusId);

        save(income);
        return income;
    }

    @Override
    public Optional<EIncome> getById(Integer incomeId) {
        return incomeDAO.findById(incomeId);
    }

    @Override
    public EIncome getById(Integer incomeId, Boolean handleException) {
        Optional<EIncome> income = getById(incomeId);
        if (!income.isPresent() && handleException) {
            throw new NotFoundException("income with specified id not found", "incomeId");
        }
        return income.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EIncome> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EIncome> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EIncome>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "income");

        Specification<EIncome> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return incomeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EIncome income) {
        incomeDAO.save(income);
    }

    public void setFarm(EIncome income, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        income.setFarm(farm);
    }

    public void setIncomeType(EIncome income, Integer incomeTypeId) {
        if (incomeTypeId == null) { return; }

        EIncomeType incomeType = sIncomeType.getById(incomeTypeId, true);
        income.setIncomeType(incomeType);
    }

    public void setStatus(EIncome income, Integer statusId) {
        if (income == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        income.setStatus(status);
    }

    @Override
    public EIncome update(Integer incomeId, IncomeDTO incomeDTO) {

        EIncome income = getById(incomeId, true);
        if (incomeDTO.getAmount() != null) {
            income.setAmount(incomeDTO.getAmount());
        }
        if (incomeDTO.getReference() != null) {
            income.setReference(incomeDTO.getReference());
        }
        income.setUpdatedOn(LocalDateTime.now());
        setFarm(income, incomeDTO.getFarmId());
        setIncomeType(income, incomeDTO.getIncomeTypeId());
        setStatus(income, incomeDTO.getStatusId());

        save(income);
        return income;
    }
    
}

// TODO: Create transaction
