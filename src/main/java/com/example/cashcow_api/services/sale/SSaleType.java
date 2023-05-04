package com.example.cashcow_api.services.sale;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.sales.SaleTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ESaleType;
import com.example.cashcow_api.repositories.SaleTypeDAO;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SSaleType implements ISaleType {

    @Autowired
    private SaleTypeDAO saleTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ESaleType create(SaleTypeDTO saleTypeDTO) {

        ESaleType saleType = new ESaleType();
        saleType.setDescription(saleTypeDTO.getDescription());
        saleType.setName(saleTypeDTO.getName());

        save(saleType);
        return saleType;
    }

    @Override
    public Optional<ESaleType> getById(Integer id) {
        return saleTypeDAO.findById(id);
    }

    @Override
    public ESaleType getById(Integer id, Boolean handleException) {
        Optional<ESaleType> saleType = getById(id);
        if (!saleType.isPresent() && handleException) {
            throw new NotFoundException("sale type with specified id not found", "saleTypeId");
        }
        return saleType.get();
    }

    @Override
    public List<ESaleType> getList() {
        return saleTypeDAO.findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ESaleType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ESaleType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ESaleType>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "saleType");

        Specification<ESaleType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return saleTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ESaleType saleType) {
        saleTypeDAO.save(saleType);
    }

    @Override
    public ESaleType update(Integer id, SaleTypeDTO saleTypeDTO) {

        ESaleType saleType = getById(id, true);
        if (saleTypeDTO.getDescription() != null) {
            saleType.setDescription(saleTypeDTO.getDescription());
        }
        if (saleTypeDTO.getName() != null) {
            saleType.setName(saleTypeDTO.getName());
        }

        save(saleType);
        return saleType;
    }
    
}
