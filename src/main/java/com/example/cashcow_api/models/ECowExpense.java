package com.example.cashcow_api.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.example.cashcow_api.models.composites.CowExpensePK;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cow_expenses")
@Data
@NoArgsConstructor
public class ECowExpense implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CowExpensePK cowExpensePK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cow_id", referencedColumnName = "id")
    @MapsId(value = "cow_id")
    private ECow cow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", referencedColumnName = "id")
    @MapsId(value = "expense_id")
    private EExpense expense;

    public ECowExpense(ECow cow, EExpense expense) {
        setCow(cow);
        setExpense(expense);
        setCowExpensePK(new CowExpensePK(cow.getId(), expense.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        ECowExpense cowExpense = (ECowExpense) o;
        return cow.getId() == cowExpense.getCow().getId();
    }

    @Override
    public int hashCode() {
        int hash = 65;
        hash = 31 * hash + cow.getId().intValue();
        return hash;
    }
}
