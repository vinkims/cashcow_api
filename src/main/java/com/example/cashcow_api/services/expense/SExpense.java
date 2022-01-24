package com.example.cashcow_api.services.expense;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.expense.ExpenseDTO;
import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.EExpense;
import com.example.cashcow_api.models.EExpenseType;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.ExpenseDAO;
import com.example.cashcow_api.services.cow.ICow;
import com.example.cashcow_api.services.status.IStatus;
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
public class SExpense implements IExpense {

    @Value(value = "${default.value.status.pending-id}")
    private Integer pendingStatusId;

    @Autowired
    private ExpenseDAO expenseDAO;

    @Autowired
    private ICow sCow;

    @Autowired
    private IExpenseType sExpenseType;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EExpense create(ExpenseDTO expenseDTO) {
        
        EExpense expense = new EExpense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setCreatedOn(LocalDateTime.now());
        if (expenseDTO.getDescription() != null){
            expense.setDescription(expenseDTO.getDescription());
        }
        setCow(expense, expenseDTO.getCowId());
        setExpenseType(expense, expenseDTO.getExpenseTypeId());

        Integer statusId = expenseDTO.getStatusId() != null ? expenseDTO.getStatusId() : pendingStatusId;
        setStatus(expense, statusId);

        setUser(expense, expenseDTO.getUserId());

        save(expense);

        return expense;
    }

    @Override
    public Optional<EExpense> getById(Integer id) {
        return expenseDAO.findById(id);
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

    public void setCow(EExpense expense, Integer cowId){
        
        if (cowId == null) { return; }
        Optional<ECow> cow = sCow.getById(cowId);
        if (!cow.isPresent()){
            throw new NotFoundException("cow with specified id not found", "cowId");
        }
        expense.setCow(cow.get());
    }

    public void setExpenseType(EExpense expense, Integer expenseTypeId){

        if (expenseTypeId == null){ return; }
        Optional<EExpenseType> expenseType = sExpenseType.getById(expenseTypeId);
        if (!expenseType.isPresent()){
            throw new NotFoundException("expense type with specidied id not found", "expenseTypeId");
        }
        expense.setExpenseType(expenseType.get());
    }

    public void setStatus(EExpense expense, Integer statusId){

        Optional<EStatus> status = sStatus.getById(statusId);
        if (!status.isPresent()){
            throw new NotFoundException("status with specified id not found", "statusId");
        }
        expense.setStatus(status.get());
    }
    
    public void setUser(EExpense expense, Integer userId){
        
        if (userId == null){ return; }
        Optional<EUser> user = sUser.getById(userId);
        if (!user.isPresent()){
            throw new NotFoundException("user with specified id not found", "userId");
        }
        expense.setUser(user.get());
    }
}
