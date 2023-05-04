package com.example.cashcow_api.services.expense;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EExpense;
import com.example.cashcow_api.models.EExpenseType;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.ExpenseDAO;
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
public class SExpense implements IExpense {

    @Value(value = "${default.value.status.pending-id}")
    private Integer pendingStatusId;

    @Autowired
    private ExpenseDAO expenseDAO;

    @Autowired
    private IExpenseType sExpenseType;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EExpense create(ExpenseDTO expenseDTO) {
        
        EExpense expense = new EExpense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setCreatedOn(LocalDateTime.now());
        expense.setDescription(expenseDTO.getDescription());
        setExpenseType(expense, expenseDTO.getExpenseTypeId());
        Integer statusId = expenseDTO.getStatusId() != null ? expenseDTO.getStatusId() : pendingStatusId;
        setStatus(expense, statusId);

        save(expense);
        return expense;
    }

    @Override
    public Optional<EExpense> getById(Integer id) {
        return expenseDAO.findById(id);
    }

    @Override
    public EExpense getById(Integer id, Boolean handleException) {
        Optional<EExpense> expense = getById(id);
        if (!expense.isPresent() && handleException) {
            throw new NotFoundException("expense with specified id not found", "expenseId");
        }
        return expense.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EExpense> getPaginatedList(PageDTO pageDTO, List<String> allowableFields){

        String search = pageDTO.getSearch();

        SpecBuilder<EExpense> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<EExpense>) specFactory.generateSpecification(search, specBuilder, allowableFields, "expense");
        Specification<EExpense> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(),
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return expenseDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EExpense expense) {
        expenseDAO.save(expense);
    }

    public void setExpenseType(EExpense expense, Integer expenseTypeId){
        if (expenseTypeId == null){ return; }

        EExpenseType expenseType = sExpenseType.getById(expenseTypeId, true);
        expense.setExpenseType(expenseType);
    }

    public void setStatus(EExpense expense, Integer statusId){
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        expense.setStatus(status);
    }

    @Override
    public EExpense update(Integer id, ExpenseDTO expenseDTO) {

        EExpense expense = getById(id, true);
        if (expenseDTO.getAmount() != null) {
            expense.setAmount(expenseDTO.getAmount());
        }
        if (expenseDTO.getDescription() != null) {
            expense.setDescription(expenseDTO.getDescription());
        }
        expense.setUpdatedOn(LocalDateTime.now());
        setExpenseType(expense, expenseDTO.getExpenseTypeId());
        setStatus(expense, expenseDTO.getStatusId());

        save(expense);
        return expense;
    }

    // TODO: Update transaction
    
}
