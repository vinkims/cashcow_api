package com.example.cashcow_api.services.weight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.weight.WeightDTO;
import com.example.cashcow_api.dtos.weight.WeightSummaryDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.EWeight;
import com.example.cashcow_api.repositories.WeightDAO;
import com.example.cashcow_api.services.cow.ICow;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SWeight implements IWeight {

    @Autowired
    private ICow sCow;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private WeightDAO weightDAO;

    @Override
    public EWeight create(WeightDTO weightDTO) {
        
        EWeight weight = new EWeight();
        weight.setCreatedOn(LocalDateTime.now());
        weight.setWeight(weightDTO.getWeight());
        setCow(weight, weightDTO.getCowId());
        
        save(weight);

        return weight;
    }

    @Override
    public Optional<EWeight> getById(Integer id) {
        return weightDAO.findById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EWeight> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EWeight> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<EWeight>) specFactory.generateSpecification(search, specBuilder, allowableFields, "weight");
        Specification<EWeight> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));
        
        return weightDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EWeight weight) {
        weightDAO.save(weight);
    }

    public void setCow(EWeight weight, Integer cowId){

        Optional<ECow> cow = sCow.getById(cowId);
        if (!cow.isPresent()){
            throw new NotFoundException("cow with specified id not found", "cowId");
        }
        weight.setCow(cow.get());
    }
    
}
