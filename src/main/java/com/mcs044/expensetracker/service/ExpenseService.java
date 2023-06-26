package com.mcs044.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.entity.Employee;
import com.mcs044.expensetracker.entity.Expense;
import com.mcs044.expensetracker.repository.ConsumerRepository;
import com.mcs044.expensetracker.repository.EmployeeRepository;
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
    private EmployeeRepository employeeRepository;

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
        boolean editCall = expense.getId() != null && !expense.getId().equals(0L);
        double oldAmount = 0;
        Employee employee = null;
        if (editCall) {
            Expense oldExpense = expenseRepository.findById(expense.getId()).orElse(null);
            oldAmount = oldExpense.getAmount();
            employee = expense.getEmployee();
        }
        if (expense.getCategory().equalsIgnoreCase("Personnel Costs"))
            employee = employeeRepository.findById(expense.getEmployeeIdInt()).get();
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
            if (expense.getCategory().equalsIgnoreCase("Personnel Costs"))
                newExpense.setEmployee(employee);
            returned = expenseRepository.save(newExpense);
            reportService.update(newExpense, editCall, oldAmount);
        }
        return returned;
    }

    public Expense updateExpense(Long id, Long userId, Expense expenseDetails) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);
        Expense expense = expenseOptional.get();
        double oldAmount = expense.getAmount();
        expense.setCategory(expenseDetails.getCategory());
        expense.setAmount(expenseDetails.getAmount());
        expense.setDate(expenseDetails.getDate());
        expense.setDescription(expenseDetails.getDescription());
        Expense updatedExpense = expenseRepository.save(expense);
        reportService.update(updatedExpense, true, oldAmount);
        return updatedExpense;
    }

    @Transactional
    public boolean deleteExpense(Long userId, Long id) throws Exception {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        if (consumer.isPresent()) {
            Expense deletedExpense = expenseRepository.findById(id).get();
            expenseRepository.deleteByIdAndConsumer(id, consumer.get());
            reportService.delete(deletedExpense);
            return true;
        } else
            throw new Exception("Consumer with the userId not found!");
    }

}
