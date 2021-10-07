package com.example.cashcow_api.services.cow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowServiceDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowService;
import com.example.cashcow_api.models.ECowServiceType;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.CowServiceDAO;
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
public class SCowService implements ICowService {

    @Autowired
    private CowServiceDAO cowServiceDAO;

    @Autowired
    private ICow sCow;

    @Autowired
    private ICowServiceType sCowServiceType;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IUser sUser;

    @Override
    public ECowService create(CowServiceDTO cowServiceDTO) {
        
        ECowService cowService = new ECowService();
        cowService.setAmount(cowServiceDTO.getAmount());
        cowService.setResults(cowServiceDTO.getResults());
        cowService.setCreatedOn(LocalDateTime.now());
        setCow(cowService, cowServiceDTO.getCowId());
        setCowServiceType(cowService, cowServiceDTO.getCowServiceTypeId());
        setUser(cowService, cowServiceDTO.getUserId());

        save(cowService);

        return cowService;
    }

    @Override
    public Optional<ECowService> getById(Integer cowServiceId) {
        return cowServiceDAO.findById(cowServiceId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<ECowService> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ECowService> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<ECowService>) specFactory.generateSpecification(search, specBuilder, allowableFields, "cowService");
        Specification<ECowService> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cowServiceDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECowService cowService) {
        cowServiceDAO.save(cowService);
    }

    public void setCow(ECowService cowService, Integer cowId){

        Optional<ECow> cow = sCow.getById(cowId);
        if (!cow.isPresent()){
            throw new NotFoundException("cow with specified id not found", "cowId");
        }
        cowService.setCow(cow.get());
    }

    public void setCowServiceType(ECowService cowService, Integer serviceTypeId){

        Optional<ECowServiceType> serviceType = sCowServiceType.getById(serviceTypeId);
        if (!serviceType.isPresent()){
            throw new NotFoundException("service type with specified id not found", "servicetypeId");
        }
        cowService.setCowServiceType(serviceType.get());
    }

    public void setUser(ECowService cowService, Integer userId){

        if (userId == null){ return; }
        Optional<EUser> user = sUser.getById(userId);
        if (!user.isPresent()){
            throw new NotFoundException("user with specified id not found", "userId");
        }
        cowService.setUser(user.get());
    }

    @Override
    public ECowService update(ECowService cowService, CowServiceDTO cowServiceDTO) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
