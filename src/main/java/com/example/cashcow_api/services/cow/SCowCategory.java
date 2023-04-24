package com.example.cashcow_api.services.cow;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowCategoryDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECowCategory;
import com.example.cashcow_api.repositories.CowCategoryDAO;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SCowCategory implements ICowCategory{

    @Autowired 
    private CowCategoryDAO categoryDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ECowCategory create(CowCategoryDTO cowCategoryDTO) {

        ECowCategory category = new ECowCategory();
        category.setDescription(cowCategoryDTO.getDescription());
        category.setName(cowCategoryDTO.getName());

        save(category);
        return category;
    }
    
    @Override
    public Optional<ECowCategory> getById(Integer categoryId) {
        return categoryDAO.findById(categoryId);
    }

    @Override
    public ECowCategory getById(Integer categoryId, Boolean handleException) {
        Optional<ECowCategory> category = getById(categoryId);
        if (!category.isPresent() && handleException) {
            throw new NotFoundException("cow category with specified id not found", "cowCategoryId");
        }
        return category.get();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Page<ECowCategory> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECowCategory> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECowCategory>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowCategory");

        Specification<ECowCategory> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return categoryDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowCategory cowCategory) {
        categoryDAO.save(cowCategory);
    }

    @Override
    public ECowCategory update(Integer categoryId, CowCategoryDTO categoryDTO) {

        ECowCategory category = getById(categoryId, true);
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }
        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }

        save(category);
        return category;
    }
}
