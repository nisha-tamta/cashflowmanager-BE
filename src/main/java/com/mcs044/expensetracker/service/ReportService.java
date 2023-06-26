package com.mcs044.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcs044.expensetracker.entity.Budget;
import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.entity.Expense;
import com.mcs044.expensetracker.entity.MonthEnum;
import com.mcs044.expensetracker.entity.Report;
import com.mcs044.expensetracker.repository.ConsumerRepository;
import com.mcs044.expensetracker.repository.ReportRepository;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ConsumerRepository consumerRepository;

    public List<Report> getAllReports(Long userId) {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        if (consumer.isPresent())
            return reportRepository.findByConsumer(consumer.get());
        return null;
    }

    public void saveInitialReport(Consumer consumer) {
        Report report = new Report();
        report.setConsumer(consumer);
        report.setBudget(consumer.getDefaultBudget());
        
        report.setExpenditure(0L);
        report.setSaving(report.getBudget());

        String month = LocalDate.now().getMonth().name();
        report.setMonth(MonthEnum.valueOf(convertToFirstLetterUppercase(month)));
        reportRepository.save(report);
    }

    public Report saveInitialReportForMonth(Consumer consumer, String month) {
        Report report = new Report();
        report.setConsumer(consumer);
        report.setBudget(consumer.getDefaultBudget());
        
        report.setExpenditure(0L);
        report.setSaving(report.getBudget());

        report.setMonth(MonthEnum.valueOf(convertToFirstLetterUppercase(month)));
        return reportRepository.save(report);
    }

    public void update(Expense newExpense, boolean editCall, double oldAmount) {
        String month = newExpense.getDate().getMonth().name();
        Report report = reportRepository.findByConsumerIdAndMonth(newExpense.getConsumer().getId(), MonthEnum.valueOf(convertToFirstLetterUppercase(month)));
        if (report == null) {
            report = saveInitialReportForMonth(newExpense.getConsumer(), month);
            report.setExpenditure(report.getExpenditure() + newExpense.getAmount());
            report.setSaving(report.getSaving() - newExpense.getAmount());
            reportRepository.save(report);
        } else if (editCall){
            report.setExpenditure(report.getExpenditure() - oldAmount + newExpense.getAmount());
            report.setSaving(report.getSaving() + oldAmount - newExpense.getAmount());
            reportRepository.save(report);
        } else {
            report.setExpenditure(report.getExpenditure() + newExpense.getAmount());
            report.setSaving(report.getSaving() - newExpense.getAmount());
            reportRepository.save(report);
        }
    }

    public void delete(Expense deletedExpense) {
        String month = deletedExpense.getDate().getMonth().name();
        Report report = reportRepository.findByConsumerIdAndMonth(deletedExpense.getConsumer().getId(), MonthEnum.valueOf(convertToFirstLetterUppercase(month)));
        report.setExpenditure(report.getExpenditure() - deletedExpense.getAmount());
        report.setSaving(report.getSaving() + deletedExpense.getAmount());
        reportRepository.save(report);
    }

    private String convertToFirstLetterUppercase(String string){
        return string.substring(0, 1).toUpperCase() 
        + string.substring(1).toLowerCase();
    }

    public void updateBudget(Budget budget) {
        Consumer consumer = budget.getConsumer();
        Optional<Report> reportOpt = reportRepository.findByConsumerAndMonth(consumer, budget.getMonth());
        if(reportOpt.isPresent()){
            Report report = reportOpt.get();
            report.setBudget(budget.getAmount());
            report.setSaving(budget.getAmount() - report.getExpenditure());
            reportRepository.save(report);
        } else {
            Report report = new Report();
            report.setConsumer(consumer);
            report.setBudget(budget.getAmount());
            report.setExpenditure(0);
            report.setMonth(budget.getMonth());
            report.setSaving(budget.getAmount());
            reportRepository.save(report);
        }
    }

}
