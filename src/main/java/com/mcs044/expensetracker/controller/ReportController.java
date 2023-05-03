package com.mcs044.expensetracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcs044.expensetracker.entity.Report;
import com.mcs044.expensetracker.service.ReportService;

/*
 * Create a RESTful API controller class that will handle incoming HTTP requests
 * and interact with the ExpenseService to perform CRUD operations on expenses.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<?> getAllReports(@RequestParam("userId") Long userId) {
        try {
            List<Report> reports = reportService.getAllReports(userId);
            return ResponseEntity.ok(reports);
        } catch (Exception ex) {
            String errorMessage = "Error during getting expenses: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorMessage);
        }
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getReports(@RequestParam("userId") Long userId,
                                            @RequestParam("month") Integer month,
                                            @RequestParam("year") Integer year) {
        try {
            List<Report> reports = reportService.geteportsByMonthAndYear(userId, month, year);
            return ResponseEntity.ok(reports);
        } catch (Exception ex) {
            String errorMessage = "Error during getting expenses: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorMessage);
        }
    }
}

