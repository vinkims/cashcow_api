package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionSummaryDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EMilkConsumption;
import com.example.cashcow_api.models.EMilkConsumptionCategory;
import com.example.cashcow_api.models.EMilkingSession;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.MilkConsumptionDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.services.user.IUser;
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
public class SMilkConsumption implements IMilkConsumption {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private IMilkConsumptionCategory sConsumptionCategory;

    @Autowired
    private IMilkingSession sMilkingSession;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Autowired
    private MilkConsumptionDAO consumptionDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EMilkConsumption create(MilkConsumptionDTO consumptionDTO) {
        
        EMilkConsumption consumption = new EMilkConsumption();
        consumption.setCreatedOn(consumptionDTO.getCreatedOn());
        consumption.setQuantity(consumptionDTO.getQuantity());
        consumption.setUnitCost(consumptionDTO.getUnitCost());
        setCategory(consumption, consumptionDTO.getCategoryId());
        setFarm(consumption, consumptionDTO.getFarmId());
        setSession(consumption, consumptionDTO.getSessionId());
        Integer statusId = consumptionDTO.getStatusId() == null ? completeStatusId : consumptionDTO.getStatusId();
        setStatus(consumption, statusId);
        setUser(consumption, consumptionDTO.getUserId());

        save(consumption);

        return consumption;
    }

    @Override
    public Optional<EMilkConsumption> getById(Integer id) {
        return consumptionDAO.findById(id);
    }

    @Override
    public EMilkConsumption getById(Integer id, Boolean handleException) {
        Optional<EMilkConsumption> consumption = getById(id);
        if (!consumption.isPresent() && handleException) {
            throw new NotFoundException("milk consumption with specified id not found", "milkConsumptionId");
        }
        return consumption.get();
    }

    @Override
    public List<MilkConsumptionSummaryDTO> getMilkConsumptionSummaryByDate(LocalDateTime startDate, LocalDateTime endDate){
        return consumptionDAO.findMilkConsumtionSummary(startDate, endDate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EMilkConsumption> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EMilkConsumption> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EMilkConsumption>) specFactory.generateSpecification(
            search, specBuilder, allowableFields, "consumption");
        
        Specification<EMilkConsumption> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));
        
        return consumptionDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EMilkConsumption consumption) {
        consumptionDAO.save(consumption);
    }

    public void setCategory(EMilkConsumption consumption, Integer categoryId){
        if (categoryId == null) { return; }

        EMilkConsumptionCategory category = sConsumptionCategory.getById(categoryId, true);
        consumption.setCategory(category);
    }

    public void setFarm(EMilkConsumption consumption, Integer farmId) {
        if (farmId == null) { return; }

        EFarm farm = sFarm.getById(farmId, true);
        consumption.setFarm(farm);
    }

    public void setSession(EMilkConsumption consumption, Integer sessionId){
        if (sessionId == null ){ return; }
        
        EMilkingSession session = sMilkingSession.getById(sessionId, true);
        consumption.setSession(session);
    }

    public void setStatus(EMilkConsumption consumption, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        consumption.setStatus(status);
    }

    public void setUser(EMilkConsumption consumption, Integer userId){
        if (userId == null) { return; }
        
        EUser user = sUser.getById(userId, true);
        consumption.setUser(user);
    }

    @Override
    public EMilkConsumption update(Integer id, MilkConsumptionDTO consumptionDTO) {

        EMilkConsumption consumption = getById(id, true);
        if (consumptionDTO.getQuantity() != null) {
            consumption.setQuantity(consumptionDTO.getQuantity());
        }
        if (consumptionDTO.getUnitCost() != null) {
            consumption.setUnitCost(consumptionDTO.getUnitCost());
        }
        consumption.setUpdatedOn(LocalDateTime.now());
        setCategory(consumption, consumptionDTO.getCategoryId());
        setFarm(consumption, consumptionDTO.getFarmId());
        setSession(consumption, consumptionDTO.getSessionId());
        setStatus(consumption, consumptionDTO.getStatusId());
        setUser(consumption, consumptionDTO.getUserId());

        save(consumption);
        return consumption;
    }
    
}
