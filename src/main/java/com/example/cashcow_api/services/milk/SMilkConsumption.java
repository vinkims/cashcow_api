package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionDTO;
import com.example.cashcow_api.dtos.milk.MilkConsumptionSummaryDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkConsumption;
import com.example.cashcow_api.models.EMilkConsumptionCategory;
import com.example.cashcow_api.models.EMilkingSession;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.MilkConsumptionDAO;
import com.example.cashcow_api.services.user.IUser;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SMilkConsumption implements IMilkConsumption {

    @Autowired
    private IMilkConsumptionCategory sConsumptionCategory;

    @Autowired
    private IMilkingSession sMilkingSession;

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
        consumption.setLitrePrice(consumptionDTO.getLitrePrice());
        consumption.setQuantity(consumptionDTO.getQuantity());
        setCategory(consumption, consumptionDTO.getCategoryId());
        setSession(consumption, consumptionDTO.getSessionId());
        setUser(consumption, consumptionDTO.getUserId());

        save(consumption);

        return consumption;
    }

    @Override
    public Optional<EMilkConsumption> getById(Integer id) {
        return consumptionDAO.findById(id);
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
        Optional<EMilkConsumptionCategory> category = sConsumptionCategory.getById(categoryId);
        if (!category.isPresent()){
            throw new NotFoundException("category with specified id not found", "categoryId");
        }
        consumption.setCategory(category.get());
    }

    public void setSession(EMilkConsumption consumption, Integer sessionId){

        if (sessionId == null ){ return; }
        Optional<EMilkingSession> session = sMilkingSession.getById(sessionId);
        if (!session.isPresent()){
            throw new NotFoundException("session with specified id not found", "sessionId");
        }
        consumption.setSession(session.get());
    }

    public void setUser(EMilkConsumption consumption, Integer userId){

        if (userId == null) { return; }
        Optional<EUser> user = sUser.getById(userId);
        if (!user.isPresent()){
            throw new NotFoundException("user with specified id not found", "userId");
        }
        consumption.setUser(user.get());
    }
    
}
