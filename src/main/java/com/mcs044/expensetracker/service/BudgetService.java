package com.mcs044.expensetracker.service;

import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.entity.MonthEnum;
import com.mcs044.expensetracker.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcs044.expensetracker.entity.Budget;
import com.mcs044.expensetracker.repository.BudgetRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private BudgetRepository budgetRepository;
	@Autowired
	private ReportService reportService;

    public Budget getOverallBudget() {
        Budget budget = budgetRepository.findById(1L).orElse(new Budget());
        return budget;
    }

    public Budget setBudget(Long userId, Budget budget) {
        Budget returned = null;
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        if (consumer.isPresent()) {
            Budget newBudget = new Budget();
            newBudget.setAmount(budget.getAmount());
            newBudget.setConsumer(consumer.get());
            newBudget.setMonth(budget.getMonth());
            newBudget.setYear(budget.getYear());
            returned = budgetRepository.save(newBudget);
            reportService.updateBudget(returned);
        }
        return returned;
    }

    public void saveInitialBudget(Consumer consumer) {
        Budget budget = new Budget();
        budget.setConsumer(consumer);
        budget.setAmount(consumer.getDefaultBudget());
        String month = LocalDate.now().getMonth().name().substring(0, 1).toUpperCase() 
            + LocalDate.now().getMonth().name().substring(1).toLowerCase();
        budget.setMonth(MonthEnum.valueOf(month));
        budget.setYear(LocalDate.now().getYear());
        budgetRepository.save(budget);
    }

    public Budget getBudgetCurrentMonth(Long userId) {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        String month = LocalDate.now().getMonth().name().substring(0, 1).toUpperCase() 
            + LocalDate.now().getMonth().name().substring(1).toLowerCase();
        MonthEnum monthEnum = MonthEnum.valueOf(month);
        return budgetRepository.findByMonthAndYearAndConsumer(monthEnum, LocalDate.now().getYear(), consumer.get());
    }

    public Budget getBudgetForTime(Long userId, Integer monthInt, Integer year) {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        String monthName = Month.of(monthInt).name();
        String month = monthName.substring(0, 1).toUpperCase() 
            + monthName.substring(1).toLowerCase();
        MonthEnum monthEnum = MonthEnum.valueOf(month);
        Budget budget = budgetRepository.findByMonthAndYearAndConsumer(monthEnum, year, consumer.get());
        if (budget == null) {
            budget = new Budget();
            budget.setAmount(consumer.get().getDefaultBudget());
            budget.setConsumer(consumer.get());
            budget.setMonth(monthEnum);
            budget.setYear(year);
            return budget;
        } else return budget;
    }
}