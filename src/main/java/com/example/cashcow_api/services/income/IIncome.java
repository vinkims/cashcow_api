package com.example.cashcow_api.services.income;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.income.IncomeDTO;
import com.example.cashcow_api.models.EIncome;

public interface IIncome {
    
    EIncome create(IncomeDTO incomeDTO);

    Optional<EIncome> getById(Integer incomeId);

    EIncome getById(Integer incomeId, Boolean handleException);

    Page<EIncome> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EIncome income);

    EIncome update(Integer incomeId, IncomeDTO incomeDTO);
}
