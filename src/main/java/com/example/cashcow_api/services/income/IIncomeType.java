package com.example.cashcow_api.services.income;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.income.IncomeTypeDTO;
import com.example.cashcow_api.models.EIncomeType;

public interface IIncomeType {
    
    EIncomeType create(IncomeTypeDTO incomeTypeDTO);

    Optional<EIncomeType> getById(Integer typeId);

    EIncomeType getById(Integer typeId, Boolean handleException);

    Page<EIncomeType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EIncomeType incomeType);

    EIncomeType update(Integer typeId, IncomeTypeDTO incomeTypeDTO);
}
