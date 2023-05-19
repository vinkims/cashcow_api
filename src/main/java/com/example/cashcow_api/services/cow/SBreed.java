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

import com.example.cashcow_api.dtos.cow.BreedDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EBreed;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.BreedDAO;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

@Service
public class SBreed implements IBreed {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private BreedDAO cowBreedDAO;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return cowBreedDAO.existsByName(name);
    }

    @Override
    public EBreed create(BreedDTO cowBreedDTO) {
        EBreed cowBreed = new EBreed();
        cowBreed.setCreatedOn(LocalDateTime.now());
        cowBreed.setDescription(cowBreedDTO.getDescription());
        cowBreed.setName(cowBreedDTO.getName());
        Integer statusId = cowBreedDTO.getStatusId() == null ? activeStatusId : cowBreedDTO.getStatusId();
        setStatus(cowBreed, statusId);

        save(cowBreed);
        return cowBreed;
    }

    @Override
    public List<EBreed> getAll() {
        return cowBreedDAO.findAll();
    }

    @Override
    public Optional<EBreed> getById(Integer breedId) {
        return cowBreedDAO.findById(breedId);
    }

    @Override
    public EBreed getById(Integer breedId, Boolean handleException) {
        Optional<EBreed> cowBreed = getById(breedId);
        if (!cowBreed.isPresent() && handleException) {
            throw new NotFoundException("cow breed with specified id not found", "cowBreedId");
        }
        return cowBreed.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EBreed> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EBreed> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EBreed>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "cowBreed");

        Specification<EBreed> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cowBreedDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EBreed cowBreed) {
        cowBreedDAO.save(cowBreed);
    }

    public void setStatus(EBreed cowBreed, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        cowBreed.setStatus(status);
    }

    @Override
    public EBreed update(Integer breedId, BreedDTO cowBreedDTO) {

        EBreed cowBreed = getById(breedId, true);
        if (cowBreedDTO.getDescription() != null) {
            cowBreed.setDescription(cowBreedDTO.getDescription());
        }
        if (cowBreedDTO.getName() != null) {
            cowBreed.setName(cowBreedDTO.getName());
        }
        setStatus(cowBreed, cowBreedDTO.getStatusId());

        save(cowBreed);
        return cowBreed;
    }
    
}
