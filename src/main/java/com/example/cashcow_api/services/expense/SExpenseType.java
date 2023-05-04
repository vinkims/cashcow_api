package com.example.cashcow_api.services.expense;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.expense.ExpenseTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EExpenseType;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.ExpenseTypeDAO;
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
public class SExpenseType implements IExpenseType {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private ExpenseTypeDAO expenseTypeDAO;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return expenseTypeDAO.existsByName(name);
    }

    @Override
    public EExpenseType create(ExpenseTypeDTO expenseTypeDTO) {
        
        EExpenseType expenseType = new EExpenseType();
        expenseType.setCreatedOn(LocalDateTime.now());
        expenseType.setDescription(expenseTypeDTO.getDescription());
        expenseType.setName(expenseTypeDTO.getName());
        setFarm(expenseType, expenseTypeDTO.getFarmId());
        Integer statusId = expenseTypeDTO.getStatusId() == null ? activeStatusId : expenseTypeDTO.getStatusId();
        setStatus(expenseType, statusId);

        save(expenseType);
        return expenseType;
    }

    @Override
    public List<EExpenseType> getAll() {
        return expenseTypeDAO.findAll();
    }

    @Override
    public Optional<EExpenseType> getById(Integer expenseTypeId) {
        return expenseTypeDAO.findById(expenseTypeId);
    }

    @Override
    public EExpenseType getById(Integer expenseTypeId, Boolean handleException) {
        Optional<EExpenseType> expenseType = getById(expenseTypeId);
        if (!expenseType.isPresent() && handleException) {
            throw new NotFoundException("expense type with specified id not found", "expenseTypeId");
        }
        return expenseType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EExpenseType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EExpenseType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EExpenseType>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "expenseType");

        Specification<EExpenseType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return expenseTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EExpenseType expenseType) {
        expenseTypeDAO.save(expenseType);
    }

    public void setFarm(EExpenseType expenseType, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        expenseType.setFarm(farm);
    }

    public void setStatus(EExpenseType expenseType, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        expenseType.setStatus(status);
    }

    @Override
    public EExpenseType update(Integer expenseTypeId, ExpenseTypeDTO expenseTypeDTO) {

        EExpenseType expenseType = getById(expenseTypeId, true);
        if (expenseTypeDTO.getDescription() != null) {
            expenseType.setDescription(expenseTypeDTO.getDescription());
        }
        if (expenseTypeDTO.getName() != null) {
            expenseType.setName(expenseTypeDTO.getName());
        }
        expenseType.setUpdatedOn(LocalDateTime.now());
        setFarm(expenseType, expenseTypeDTO.getFarmId());
        setStatus(expenseType, expenseTypeDTO.getStatusId());

        save(expenseType);
        return expenseType;
    }
    
}
