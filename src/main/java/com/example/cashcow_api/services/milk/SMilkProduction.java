package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.DailyCowProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionSummaryDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.EMilkProduction;
import com.example.cashcow_api.models.EMilkingSession;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.MilkProductionDAO;
import com.example.cashcow_api.services.cow.ICow;
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
public class SMilkProduction implements IMilkProduction {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Autowired
    private ICow sCow;

    @Autowired
    private IMilkingSession sMilkingSession;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Autowired
    private MilkProductionDAO productionDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EMilkProduction create(MilkProductionDTO productionDTO) {
        
        EMilkProduction production = new EMilkProduction();
        production.setCreatedOn(productionDTO.getProductionDate());
        production.setQuantity(productionDTO.getQuantity());
        setCow(production, productionDTO.getCowId());
        setMilkingSession(production, productionDTO.getSessionId());
        setUser(production, productionDTO.getUserId());
        Integer statusId = productionDTO.getStatusId() == null ? completeStatusId : productionDTO.getStatusId();
        setStatus(production, statusId);

        save(production);
        return production;
    }

    @Override
    public Optional<EMilkProduction> getById(Integer productionId) {
        return productionDAO.findById(productionId);
    }

    @Override
    public EMilkProduction getById(Integer productionId, Boolean handleException) {
        Optional<EMilkProduction> production = getById(productionId);
        if (!production.isPresent() && handleException) {
            throw new NotFoundException("milk production with specified id not found", "milkProductionId");
        }
        return production.get();
    }

    @Override
    public List<DailyCowProductionDTO> getDailyCowProduction(LocalDateTime startDate, LocalDateTime endDate, Integer cowId){
        return productionDAO.findDailyProductionByCow(startDate, endDate, cowId);
    }

    @Override
    public List<MilkProductionSummaryDTO> getMilkProductionSummary(LocalDateTime startDate, LocalDateTime endDate){
        return productionDAO.findMilkProductionSummary(startDate, endDate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EMilkProduction> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EMilkProduction> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<EMilkProduction>) specFactory.generateSpecification(search, specBuilder, allowableFields, "milkProduction");
        Specification<EMilkProduction> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return productionDAO.findAll(spec, pageRequest);
    }

    @Override
    public List<MilkProductionSummaryDTO> getProductionSummaryByCow(LocalDateTime startDate, LocalDateTime endDate, Integer cowId){
        return productionDAO.findProductionSummaryByCow(startDate, endDate, cowId);
    }

    @Override
    public void save(EMilkProduction production) {
        productionDAO.save(production);
    }

    public void setCow(EMilkProduction production, Integer cowId){
        if(cowId == null){ return; }

        ECow cow = sCow.getById(cowId, true);
        production.setCow(cow);
    }

    public void setMilkingSession(EMilkProduction production, Integer sessionId){
        if (sessionId == null) { return; }

        EMilkingSession session = sMilkingSession.getById(sessionId, true);
        production.setMilkingSession(session);
    }

    public void setStatus(EMilkProduction production, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        production.setStatus(status);
    }

    public void setUser(EMilkProduction production, Integer userId){
        if (userId == null) { return; }

        EUser user = sUser.getById(userId, true);
        production.setUser(user);
    }

    @Override
    public EMilkProduction update(Integer productionId, MilkProductionDTO productionDTO) {

        EMilkProduction production = getById(productionId, true);
        if (productionDTO.getQuantity() != null) {
            production.setQuantity(productionDTO.getQuantity());
        }
        production.setUpdatedOn(LocalDateTime.now());
        setCow(production, productionDTO.getCowId());
        setMilkingSession(production, productionDTO.getSessionId());
        setStatus(production, productionDTO.getStatusId());
        setUser(production, productionDTO.getUserId());

        save(production);
        return production;
    }
    
}

//TODO: check for duplicate production records