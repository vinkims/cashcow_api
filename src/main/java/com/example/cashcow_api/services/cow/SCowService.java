package com.example.cashcow_api.services.cow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowServiceDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowService;
import com.example.cashcow_api.models.ECowServiceType;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.CowServiceDAO;
import com.example.cashcow_api.services.transaction.ITransaction;
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
public class SCowService implements ICowService {

    @Value(value = "${default.value.transaction-type.cow-service-type-id}")
    private Integer cowServiceTypeId;

    @Value(value = "${default.value.payment-channel.mpesa-channel-id}")
    private Integer mpesaPaymentChannelId;

    @Autowired
    private CowServiceDAO cowServiceDAO;

    @Autowired
    private ICow sCow;

    @Autowired
    private ICowServiceType sCowServiceType;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private ITransaction sTransaction;

    @Autowired
    private IUser sUser;

    @Override
    public ECowService create(CowServiceDTO cowServiceDTO) {
        
        ECowService cowService = new ECowService();
        if (cowServiceDTO.getAmount() != null){
            cowService.setAmount(cowServiceDTO.getAmount());
        }
        cowService.setCreatedOn(LocalDateTime.now());
        if (cowServiceDTO.getResults() != null){
            cowService.setResults(cowServiceDTO.getResults());
        }
        setBull(cowService, cowServiceDTO.getBullId());
        setCalvingDate(cowService, cowServiceDTO.getCalvingDate());
        setCow(cowService, cowServiceDTO.getCowId());
        setCowServiceType(cowService, cowServiceDTO.getCowServiceTypeId());
        setObservationDate(cowService, cowServiceDTO.getObservationDate());
        setUser(cowService, cowServiceDTO.getUserId());

        save(cowService);

        if (cowServiceDTO.getAmount() != null){
            createServiceTransaction(
                cowServiceDTO.getAmount(), 
                mpesaPaymentChannelId, 
                cowServiceTypeId, 
                String.format("Service id: %s", cowService.getId())
            );
        }

        return cowService;
    }

    /**
     * Create cow service payment transaction
     */
    public void createServiceTransaction(Float amount, Integer paymentChannelId, 
            Integer transactionTypeId, String reference){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setPaymentChannelId(paymentChannelId);
        transactionDTO.setReference(reference);
        transactionDTO.setTransactionTypeId(transactionTypeId);
        sTransaction.create(transactionDTO);
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

    public void setBull(ECowService cowService, Integer bullId){

        if (bullId == null){ return; }
        Optional<ECow> bull = sCow.getById(bullId);
        if (!bull.isPresent()){
            throw new NotFoundException("bull with specified id not found", "bullId");
        }
        cowService.setBull(bull.get());
    }

    public void setCalvingDate(ECowService cowService, String calvingDate){
        if (calvingDate != null){
            cowService.setCalvingDate(LocalDate.parse(calvingDate));
        }
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

    public void setObservationDate(ECowService cowService, String observationDate){
        if (observationDate != null){
            cowService.setObservationDate(LocalDate.parse(observationDate));
        }
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
