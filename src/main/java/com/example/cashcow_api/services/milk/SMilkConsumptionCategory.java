package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionCategoryDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EMilkConsumptionCategory;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.MilkConsumptionCategoryDAO;
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
public class SMilkConsumptionCategory implements IMilkConsumptionCategory {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private MilkConsumptionCategoryDAO categoryDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EMilkConsumptionCategory create(MilkConsumptionCategoryDTO categoryDTO) {
        EMilkConsumptionCategory category = new EMilkConsumptionCategory();
        category.setCreatedOn(LocalDateTime.now());
        category.setDescription(categoryDTO.getDescription());
        category.setName(categoryDTO.getName());
        setFarm(category, categoryDTO.getFarmId());
        Integer statusId = categoryDTO.getStatusId() == null ? activeStatusId : categoryDTO.getStatusId();
        setStatus(category, statusId);

        save(category);
        return category;
    }

    @Override
    public List<EMilkConsumptionCategory> getAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Optional<EMilkConsumptionCategory> getById(Integer categoryId) {
        return categoryDAO.findById(categoryId);
    }

    @Override
    public EMilkConsumptionCategory getById(Integer categoryId, Boolean handleException) {
        Optional<EMilkConsumptionCategory> category = getById(categoryId);
        if (!category.isPresent() && handleException) {
            throw new NotFoundException("milk consumption category with specified id not found", 
                "milkConsumptionCategoryId");
        }
        return category.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EMilkConsumptionCategory> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EMilkConsumptionCategory> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EMilkConsumptionCategory>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "milkConcumptionCategory");

        Specification<EMilkConsumptionCategory> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return categoryDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EMilkConsumptionCategory category) {
        categoryDAO.save(category);
    }

    public void setFarm(EMilkConsumptionCategory category, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        category.setFarm(farm);
    }

    public void setStatus(EMilkConsumptionCategory category, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        category.setStatus(status);
    }

    @Override
    public EMilkConsumptionCategory update(Integer categoryId, MilkConsumptionCategoryDTO categoryDTO) {

        EMilkConsumptionCategory category = getById(categoryId, true);
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }
        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
        category.setUpdatedOn(LocalDateTime.now());
        setFarm(category, categoryDTO.getFarmId());
        setStatus(category, categoryDTO.getStatusId());

        save(category);
        return category;
    }
    
}
