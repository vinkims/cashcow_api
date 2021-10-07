package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkProductionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.EMilkProduction;
import com.example.cashcow_api.models.EMilkingSession;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.MilkProductionDAO;
import com.example.cashcow_api.services.cow.ICow;
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
public class SMilkProduction implements IMilkProduction {

    @Autowired
    private ICow sCow;

    @Autowired
    private MilkProductionDAO productionDAO;

    @Autowired
    private IMilkingSession sMilkingSession;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IUser sUser;

    @Override
    public EMilkProduction create(MilkProductionDTO productionDTO) {
        
        EMilkProduction production = new EMilkProduction();
        production.setCreatedOn(LocalDateTime.now());
        production.setQuantity(productionDTO.getQuantity());
        setCow(production, productionDTO.getCowId());
        setMilkingSession(production, productionDTO.getSessionId());
        setUser(production, productionDTO.getUserId());

        save(production);
        return production;
    }

    @Override
    public Optional<EMilkProduction> getById(Integer productionId) {
        return productionDAO.findById(productionId);
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
    public void save(EMilkProduction production) {
        productionDAO.save(production);
    }

    public void setCow(EMilkProduction production, Integer cowId){
        
        if(cowId == null){ return; }
        Optional<ECow> cow = sCow.getById(cowId);
        if (!cow.isPresent()){
            throw new NotFoundException("cow with specified id not found", "cowId");
        }
        production.setCow(cow.get());
    }

    public void setMilkingSession(EMilkProduction production, Integer sessionId){

        Optional<EMilkingSession> session = sMilkingSession.getById(sessionId);
        if (!session.isPresent()){
            throw new NotFoundException("session with specified id not found", "sessionId");
        }
        production.setMilkingSession(session.get());
    }

    public void setUser(EMilkProduction production, Integer userId){

        Optional<EUser> user = sUser.getById(userId);
        if (!user.isPresent()){
            throw new NotFoundException("user with specified id not found", "userId");
        }
        production.setUser(user.get());
    }
    
}
