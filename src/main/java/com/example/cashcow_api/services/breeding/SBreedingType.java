package com.example.cashcow_api.services.breeding;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.cashcow_api.dtos.breeding.BreedingTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EBreedingType;
import com.example.cashcow_api.repositories.BreedingTypeDAO;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SBreedingType implements IBreedingType {

    @Autowired
    private BreedingTypeDAO breedingTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EBreedingType create(BreedingTypeDTO breedingTypeDTO) {

        EBreedingType breedingType = new EBreedingType();
        breedingType.setDescription(breedingTypeDTO.getDescription());
        breedingType.setName(breedingTypeDTO.getName());

        save(breedingType);
        return breedingType;
    }

    @Override
    public Optional<EBreedingType> getById(Integer typeId) {
        return breedingTypeDAO.findById(typeId);
    }

    @Override
    public EBreedingType getById(Integer typeId, Boolean handleException) {
        Optional<EBreedingType> breedingType = getById(typeId);
        if (!breedingType.isPresent() && handleException) {
            throw new NotFoundException("breeding type with specified id not found", "breedingTypeId");
        }
        return breedingType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EBreedingType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EBreedingType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EBreedingType>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "breedingType");

        Specification<EBreedingType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return breedingTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EBreedingType breedingType) {
        breedingTypeDAO.save(breedingType);
    }

    @Override
    public EBreedingType update(Integer typeId, BreedingTypeDTO breedingTypeDTO) {

        EBreedingType breedingType = getById(typeId, true);
        if (breedingTypeDTO.getDescription() != null) {
            breedingType.setDescription(breedingTypeDTO.getDescription());
        }
        if (breedingTypeDTO.getName() != null) {
            breedingType.setName(breedingTypeDTO.getName());
        }

        save(breedingType);
        return breedingType;
    }
    
}
