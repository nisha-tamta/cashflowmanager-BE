package com.mcs044.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.entity.Expense;

/*
 * Create a repository interface that extends JpaRepository to interact with the expense entity in the database.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    public List<Expense> findByConsumer(Consumer consumer);
    public void deleteByIdAndConsumer(Long id, Consumer consumer);
    public List<Expense> findByConsumerAndDateBetween(Consumer consumer, LocalDate startDate, LocalDate endDate);

}
