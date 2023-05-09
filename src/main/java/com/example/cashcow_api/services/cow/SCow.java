package com.example.cashcow_api.services.cow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.dtos.cow.CowWeightDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EBreed;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowCategory;
import com.example.cashcow_api.models.ECowImage;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.CowDAO;
import com.example.cashcow_api.services.farm.IFarm;
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
public class SCow implements ICow {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Value(value = "${default.value.category.cow-id}")
    private Integer categoryCowId;

    @Autowired
    private CowDAO cowDAO;

    @Autowired
    private IBreed sBreed;

    @Autowired
    private ICowCategory sCowCategory;

    @Autowired
    private ICowImage sCowImage;

    @Autowired
    private ICowWeight sCowWeight;

    @Autowired
    private IFarm sFarm;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IStatus sStatus;

    @Override
    public Boolean checkExistsByName(String cowName){
        return cowDAO.existsByName(cowName);
    }

    @Override
    public ECow create(CowDTO cowDTO) {

        ECow cow = new ECow();
        cow.setColor(cowDTO.getColor());
        cow.setCreatedOn(LocalDateTime.now());
        cow.setDateOfBirth(cowDTO.getDateOfBirth());
        cow.setGender(cowDTO.getGender());
        cow.setName(cowDTO.getName().toUpperCase());
        cow.setOtherDetails(cowDTO.getOtherDetails());
        Integer categoryId = cowDTO.getCategoryId() == null ?
            categoryCowId : cowDTO.getCategoryId();
        setBreed(cow, cowDTO.getBreedId());
        setCowCategory(cow, categoryId);
        setFarm(cow, cowDTO.getFarmId());
        setImage(cow, cowDTO.getImageId());
        setParent(cow, cowDTO.getParentId());

        Integer statusId = cowDTO.getStatusId() != null ? cowDTO.getStatusId() : activeStatusId;
        setStatus(cow, statusId);

        save(cow);

        recordWeight(cow.getId(), cowDTO.getWeight());
        return cow;
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
    public ECow getById(Integer cowId, Boolean handleException) {
        Optional<ECow> cow = getById(cowId);
        if (!cow.isPresent() && handleException) {
            throw new NotFoundException("cow with specified id not found", "cowid");
        }
        return cow.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<ECow> getPaginatedList(PageDTO pageDTO, List<String> allowableFields){
        
        String search = pageDTO.getSearch();

        SpecBuilder<ECow> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECow>) specFactory.generateSpecification(search, 
            specBuilder, allowableFields, "cow");
        
        Specification<ECow> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));
        
        return cowDAO.findAll(spec, pageRequest);
    }

    public void recordWeight(Integer cowId, Float weight){

        CowWeightDTO weightDTO = new CowWeightDTO();
        weightDTO.setCowId(cowId);
        weightDTO.setWeight(weight);

        sCowWeight.create(weightDTO);
    }

    @Override
    public void save(ECow cow) {
        cowDAO.save(cow);
    }

    public void setBreed(ECow cow, Integer breedId) {
        if (breedId == null) { return; }

        EBreed breed = sBreed.getById(breedId, true);
        cow.setBreed(breed);
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

    public void setImage(ECow cow, Integer imageId) {
        if (imageId == null) { return; }

        ECowImage image = sCowImage.getById(imageId, true);
        cow.setCowImage(image);
    }

    public void setParent(ECow cow, Integer parentId){

        if (parentId == null) { return; }
        Optional<ECow> parent = getById(parentId);
        if (!parent.isPresent()){
            throw new NotFoundException("parent not found", "parentId");
        }
        cow.setParent(parent.get());
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
    public ECow update(Integer cowId, CowDTO cowDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException{

        ECow cow = getById(cowId, true);

        String[] fields = {"Color", "DateOfBirth", "Gender", "Name", "OtherDetails"};
        for (String field : fields) {
            Method getField = CowDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(cowDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                ECow.class.getMethod("set" + field, fieldValue.getClass()).invoke(cow, fieldValue);
            }
        }

        cow.setUpdatedOn(LocalDateTime.now());
        setBreed(cow, cowDTO.getBreedId());
        setCowCategory(cow, cowDTO.getCategoryId());
        setFarm(cow, cowDTO.getFarmId());
        setImage(cow, cowDTO.getImageId());
        setStatus(cow, cowDTO.getStatusId());
        save(cow);

        return cow;
    }
    
}
