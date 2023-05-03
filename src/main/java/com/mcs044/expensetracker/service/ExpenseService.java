package com.mcs044.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.entity.Expense;
import com.mcs044.expensetracker.repository.ConsumerRepository;
import com.mcs044.expensetracker.repository.ExpenseRepository;

import jakarta.transaction.Transactional;

/*
 * Create a service class that will handle the business logic for managing expenses.
 * This class will use the ExpenseRepository to interact with the database.
 */
@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
	private ReportService reportService;

    public List<Expense> getAllExpenses(Long userId) {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        if (consumer.isPresent())
            return expenseRepository.findByConsumer(consumer.get());
        return null;
    }

    public List<Expense> getExpensesByMonthAndYear(Long userId, Integer month, Integer year) {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        if (consumer.isPresent()) {
            LocalDate startDate = LocalDate.of(year, month, 1); // First day of the month
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // Last day of the month
            return expenseRepository.findByConsumerAndDateBetween(consumer.get(), startDate, endDate);
        }
        return null;
    }
    

    public Expense addExpense(Long userId, Expense expense) {
        Expense returned = null;
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        if (consumer.isPresent()) {
            Expense newExpense = new Expense();
            newExpense.setId(expense.getId());
            newExpense.setCategory(expense.getCategory());
            newExpense.setAmount(expense.getAmount());
            newExpense.setDate(expense.getDate());
            newExpense.setDescription(expense.getDescription());
            newExpense.setConsumer(consumer.get());
            returned = expenseRepository.save(newExpense);
            reportService.update(newExpense);
        }
        return returned;
    }

    @Transactional
    public String deleteExpense(Long userId, Long id) throws Exception {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        if (consumer.isPresent()) {
            expenseRepository.deleteByIdAndConsumer(id, consumer.get());
            return "Success";
        }
       throw new Exception("Consumer with the userId not found!");
    }

}
