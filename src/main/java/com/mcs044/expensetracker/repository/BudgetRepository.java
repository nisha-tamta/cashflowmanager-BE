package com.mcs044.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcs044.expensetracker.entity.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
