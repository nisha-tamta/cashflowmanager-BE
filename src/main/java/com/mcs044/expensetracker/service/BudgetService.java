package com.mcs044.expensetracker.service;

import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.entity.MonthEnum;
import com.mcs044.expensetracker.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcs044.expensetracker.entity.Budget;
import com.mcs044.expensetracker.repository.BudgetRepository;

import java.time.LocalDate;
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
        budgetRepository.save(budget);
    }

    public Budget getBudgetCurrentMonth(Long userId) {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        String month = LocalDate.now().getMonth().name().substring(0, 1).toUpperCase() 
            + LocalDate.now().getMonth().name().substring(1).toLowerCase();
        MonthEnum monthEnum = MonthEnum.valueOf(month);
        return budgetRepository.findByMonthAndConsumer(monthEnum, consumer.get());
    }
}
