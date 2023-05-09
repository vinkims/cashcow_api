package com.example.cashcow_api.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.example.cashcow_api.models.composites.UserExpensePK;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user_expenses")
@Data
@NoArgsConstructor
public class EUserExpense implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserExpensePK userExpensePK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId(value = "user_id")
    private EUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", referencedColumnName = "id")
    @MapsId(value = "expense_id")
    private EExpense expense;

    public EUserExpense(EUser user, EExpense expense) {
        setUser(user);
        setExpense(expense);
        setUserExpensePK(new UserExpensePK(user.getId(), expense.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        EUserExpense userExpense = (EUserExpense) o;
        return user.getId() == userExpense.getUser().getId();
    }

    @Override
    public int hashCode() {
        int hash = 65;
        hash = 31 * hash + user.getId().intValue();
        return hash;
    }
}
