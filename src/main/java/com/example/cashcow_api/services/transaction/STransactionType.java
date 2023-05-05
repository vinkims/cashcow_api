package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.TransactionTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ETransactionType;
import com.example.cashcow_api.repositories.TransactionTypeDAO;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class STransactionType implements ITransactionType {

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private TransactionTypeDAO transactionTypeDAO;

    @Override
    public ETransactionType create(TransactionTypeDTO transactionTypeDTO) {

        ETransactionType transactionType = new ETransactionType();
        transactionType.setDescription(transactionTypeDTO.getDescription());
        transactionType.setName(transactionTypeDTO.getName());

        save(transactionType);
        return transactionType;
    }

    @Override
    public Optional<ETransactionType> getById(Integer id) {
        return transactionTypeDAO.findById(id);
    }

    @Override
    public ETransactionType getById(Integer id, Boolean handleException) {
        Optional<ETransactionType> transactionType = getById(id);
        if (!transactionType.isPresent() && handleException) {
            throw new NotFoundException("transaction type with specified id not found", "transactionTypeId");
        }
        return transactionType.get();
    }

    @Override
    public List<ETransactionType> getList() {
        return transactionTypeDAO.findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ETransactionType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ETransactionType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ETransactionType>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "transactionType");

        Specification<ETransactionType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return transactionTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ETransactionType transactionType) {
        transactionTypeDAO.save(transactionType);
    }

    @Override
    public ETransactionType update(Integer id, TransactionTypeDTO transactionTypeDTO) {

        ETransactionType transactionType = getById(id, true);
        if (transactionTypeDTO.getDescription() != null) {
            transactionType.setDescription(transactionTypeDTO.getDescription());
        }
        if (transactionTypeDTO.getName() != null) {
            transactionType.setName(transactionTypeDTO.getName());
        }

        save(transactionType);
        return transactionType;
    }
    
}
