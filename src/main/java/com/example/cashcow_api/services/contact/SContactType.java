package com.example.cashcow_api.services.contact;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.contact.ContactTypeDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EContactType;
import com.example.cashcow_api.repositories.ContactTypeDAO;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SContactType implements IContactType {
    
    @Autowired 
    private ContactTypeDAO contactTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EContactType create(ContactTypeDTO contactTypeDTO) {

        EContactType contactType = new EContactType();
        contactType.setDescription(contactTypeDTO.getDescription());
        contactType.setName(contactTypeDTO.getName());
        contactType.setRegexValue(contactTypeDTO.getRegexValue());

        save(contactType);
        return contactType;
    }

    @Override
    public List<EContactType> getAll(){
        return contactTypeDAO.findAll();
    }

    /**
     * Get contact type by id
     * @param contactTypeId
     * @return
     */
    @Override
    public Optional<EContactType> getById(Integer contactTypeId){
        return contactTypeDAO.findById(contactTypeId);
    }

    @Override
    public EContactType getById(Integer contactTypeId, Boolean handleException) {
        Optional<EContactType> contactType = getById(contactTypeId);
        if (!contactType.isPresent() && handleException) {
            throw new NotFoundException("contact type with specified id not found", "contactTypeId");
        }
        return contactType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EContactType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EContactType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EContactType>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "contactType");

        Specification<EContactType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return contactTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EContactType contactType){
        contactTypeDAO.save(contactType);
    }

    @Override
    public EContactType update(Integer contactTypeId, ContactTypeDTO contactTypeDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        EContactType contactType = getById(contactTypeId, true);
        
        String[] fields = {"Name", "Description", "RegexValue"};
        for (String field : fields) {
            Method getField = ContactTypeDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(contactTypeDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EContactType.class.getMethod("set" + field, fieldValue.getClass()).invoke(contactType, fieldValue);
            }
        }

        save(contactType);
        return contactType;
    }
}
