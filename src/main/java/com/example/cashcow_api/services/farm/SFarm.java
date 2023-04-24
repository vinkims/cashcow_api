package com.example.cashcow_api.services.farm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.FarmDAO;
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
public class SFarm implements IFarm {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;
    
    @Autowired 
    private FarmDAO farmDAO;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String farmName){
        return farmDAO.existsByName(farmName);
    }

    @Override
    public EFarm create(FarmDTO farmDTO){
        
        EFarm farm = new EFarm();
        farm.setName(farmDTO.getName().toUpperCase());
        farm.setCreatedOn(LocalDateTime.now());
        Integer statusId = farmDTO.getStatusId() == null ? activeStatusId : farmDTO.getStatusId();
        setStatus(farm, statusId);

        save(farm);
        return farm;
    }

    @Override
    public List<EFarm> getAll(){
        return farmDAO.findAll();
    }

    @Override
    public Optional<EFarm> getById(Integer farmId){
        return farmDAO.findById(farmId);
    }

    @Override
    public EFarm getById(Integer farmId, Boolean handleException) {
        Optional<EFarm> farm = getById(farmId);
        if (!farm.isPresent() && handleException) {
            throw new NotFoundException("farm with specified id not found", "farmId");
        }
        return farm.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EFarm> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EFarm> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EFarm>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "farm");

        Specification<EFarm> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return farmDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EFarm farm){
        farmDAO.save(farm);
    }

    public void setStatus(EFarm farm, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        farm.setStatus(status);
    }

    @Override
    public EFarm update(Integer farmId, FarmDTO farmDTO) {

        EFarm farm = getById(farmId, true);
        if (farmDTO.getName() != null) {
            farm.setName(farmDTO.getName());
        }
        farm.setUpdatedOn(LocalDateTime.now());
        setStatus(farm, farmDTO.getStatusId());

        save(farm);
        return farm;
    }
}
