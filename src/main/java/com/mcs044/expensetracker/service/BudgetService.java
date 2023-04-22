package com.mcs044.expensetracker.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcs044.expensetracker.entity.Budget;
import com.mcs044.expensetracker.repository.BudgetRepository;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public BigDecimal getOverallBudget() {
        Budget budget = budgetRepository.findById(1L).orElse(new Budget());
        return budget.getOverallBudget();
    }

    public BigDecimal setOverallBudget(BigDecimal overallBudget) {
        Budget budget = budgetRepository.findById(1L).orElse(new Budget());
        budget.setOverallBudget(overallBudget);
        budget = budgetRepository.save(budget);
        return budget.getOverallBudget();
    }
}
