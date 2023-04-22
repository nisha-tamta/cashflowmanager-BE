package com.mcs044.expensetracker.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Budget {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "budget_id_seq")
    @SequenceGenerator(name = "budget_id_seq", sequenceName = "budget_id_seq", allocationSize = 1)
    private Long id;

    private BigDecimal overallBudget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOverallBudget() {
        return overallBudget;
    }

    public void setOverallBudget(BigDecimal overallBudget) {
        this.overallBudget = overallBudget;
    }

}
