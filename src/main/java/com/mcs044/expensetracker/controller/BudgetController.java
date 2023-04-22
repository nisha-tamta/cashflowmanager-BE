package com.mcs044.expensetracker.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcs044.expensetracker.service.BudgetService;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<BigDecimal> getOverallBudget() {
        BigDecimal overallBudget = budgetService.getOverallBudget();
        return ResponseEntity.ok(overallBudget);
    }

    @CrossOrigin
    @PutMapping
    public BigDecimal setOverallBudget(@RequestBody BigDecimal overallBudget) {
        return budgetService.setOverallBudget(overallBudget);
    }
}


