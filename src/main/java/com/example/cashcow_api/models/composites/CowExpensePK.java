package com.example.cashcow_api.models.composites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class CowExpensePK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "cow_id")
    private Integer cowId;

    @Column(name = "expense_id")
    private Integer expenseId;

    public CowExpensePK(Integer cowId, Integer expenseId) {
        setCowId(cowId);
        setExpenseId(expenseId);
    }
}
