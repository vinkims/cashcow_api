package com.example.cashcow_api.models.composites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class UserExpensePK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "expense_id")
    private Integer expenseId;

    public UserExpensePK(Integer userId, Integer expenseId) {
        setUserId(userId);
        setExpenseId(expenseId);
    }
}
