package com.example.cashcow_api.services.cow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.dtos.cow.CowProfileDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.dtos.weight.WeightDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowCategory;
import com.example.cashcow_api.models.ECowProfile;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.CowDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.services.transaction.ITransaction;
import com.example.cashcow_api.services.weight.IWeight;
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
public class SCow implements ICow {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Value(value = "${default.value.category.cow-id}")
    private Integer categoryCowId;

    @Value(value = "${default.value.transaction-type.cow-sale-type-id}")
    private Integer cowSaleTypeId;

    @Value(value = "${default.value.payment-channel.mpesa-channel-id}")
    private Integer mpesaPaymentChannelId;

    @Value(value = "${default.value.status.pre-calving-id}")
    private Integer preCalvingStatusId;

    @Value(value = "${default.value.status.sold-id}")
    private Integer soldStatusId;

    @Autowired
    private CowDAO cowDAO;

    @Autowired
    private ICowCategory sCowCategory;

    @Autowired
    private SCowProfile sCowProfile;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private ITransaction sTransaction;

    @Autowired
    private IWeight sWeight;

    @Override
    public Boolean checkExistsByName(String cowName){
        return cowDAO.existsByName(cowName);
    }

    @Override
    public ECow create(CowDTO cowDTO) {

        ECow cow = new ECow();
        cow.setCreatedOn(LocalDateTime.now());
        cow.setName(cowDTO.getName().toUpperCase());
        Integer categoryId = cowDTO.getCategoryId() == null ?
                categoryCowId : cowDTO.getCategoryId();
        setCowCategory(cow, categoryId);
        setFarm(cow, cowDTO.getFarmId());
        setParent(cow, cowDTO.getParentId());

        Integer statusId = cowDTO.getStatusId() != null ? cowDTO.getStatusId() : activeStatusId;
        setStatus(cow, statusId);

        save(cow);

        setProfile(cow, cowDTO.getProfile());
        recordWeight(cow.getId(), cowDTO.getWeight());
        return cow;
    }

    public void createSaleTransaction(Float amount, Integer paymentChannelId, 
            Integer transactionTypeId, String reference){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setPaymentChannelId(paymentChannelId);
        transactionDTO.setReference(reference);
        transactionDTO.setTransactionTypeId(transactionTypeId);
        sTransaction.create(transactionDTO);
    }

    @Override
    public List<ECow> getAll() {
        return cowDAO.findAll();
    }

    @Override
    public Optional<ECow> getById(Integer cowId) {
        return cowDAO.findById(cowId);
    }

    @Override
    public List<ECow> getCowsByGender(String gender){
        return cowDAO.findCowsByGender(gender);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<ECow> getPaginatedList(PageDTO pageDTO, List<String> allowableFields){
        
        String search = pageDTO.getSearch();

        SpecBuilder<ECow> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<ECow>) specFactory.generateSpecification(search, specBuilder, allowableFields, "cow");
        Specification<ECow> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));
        
        return cowDAO.findAll(spec, pageRequest);
    }

    public void recordWeight(Integer cowId, Float weight){

        WeightDTO weightDTO = new WeightDTO();
        weightDTO.setCowId(cowId);
        weightDTO.setWeight(weight);

        sWeight.create(weightDTO);
    }

    @Override
    public void save(ECow cow) {
        cowDAO.save(cow);
    }

    /**
     * Sets the cow category
     * @param cow
     * @param categoryId
     */
    public void setCowCategory(ECow cow, Integer categoryId){
        if (categoryId == null){ return; }

        Optional<ECowCategory> category = sCowCategory.getById(categoryId);
        if (!category.isPresent()){
            throw new NotFoundException("cow category with specified id not found", "categoryId");
        }
        cow.setCategory(category.get());
    }

    public void setFarm(ECow cow, Integer farmId){
        
        Optional<EFarm> farm = sFarm.getById(farmId);
        if (!farm.isPresent()){
            throw new NotFoundException("farm with specified id not found", "farmId");
        }
        cow.setFarm(farm.get());
    }

    public void setParent(ECow cow, Integer parentId){

        if (parentId == null) { return; }
        Optional<ECow> parent = getById(parentId);
        if (!parent.isPresent()){
            throw new NotFoundException("parent not found", "parentId");
        }
        cow.setParent(parent.get());

        //updateParent(parent.get());
    }

    public void setProfile(ECow cow, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO  != null){
            ECowProfile profile = sCowProfile.create(cowProfileDTO, cow);
            sCowProfile.save(profile);
            cow.setProfile(profile);
        }
    }

    public void setStatus(ECow cow, Integer statusId){

        if (statusId == null){ return; }
        Optional<EStatus> status = sStatus.getById(statusId);
        if (!status.isPresent()){
            throw new NotFoundException("status with specified id not found", "statusId");
        }
        cow.setStatus(status.get());
    }

    @Override
    public ECow update(ECow cow, CowDTO cowDTO){

        if (cowDTO.getName() != null){
            cow.setName(cowDTO.getName());
        }
        setCowCategory(cow, cowDTO.getCategoryId());
        setStatus(cow, cowDTO.getStatusId());
        save(cow);

        setProfile(cow, cowDTO.getProfile());
        if (cowDTO.getStatusId() != null && cowDTO.getStatusId().equals(soldStatusId)){
            createSaleTransaction(
                cowDTO.getProfile().getSaleAmount(), 
                mpesaPaymentChannelId, 
                cowSaleTypeId,
                String.format("Cow id: %s", cow.getId())
            );
        }

        return cow;
    }

    public void updateParent(ECow cow){

        CowDTO cowDTO = new CowDTO();
        if (!cow.getCategory().getId().equals(categoryCowId)){
            cowDTO.setCategoryId(categoryCowId);
        }

        update(cow, cowDTO);
    }
    
}
