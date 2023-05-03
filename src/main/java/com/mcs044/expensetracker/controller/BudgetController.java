package com.mcs044.expensetracker.controller;

import com.mcs044.expensetracker.entity.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mcs044.expensetracker.service.BudgetService;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getOverallBudget() {
        return ResponseEntity.ok(budgetService.getOverallBudget());
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<?> addBudget(@RequestParam("userId") Long userId, @RequestBody Budget budget) {
         try {
            Budget returned = budgetService.setBudget(userId, budget);
            return ResponseEntity.ok(returned);
        } catch (Exception ex) {
            String errorMessage = "Error during set budget: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorMessage);
    }
    }
}


