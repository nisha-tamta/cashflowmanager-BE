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
import org.springframework.web.bind.annotation.RestController;

import com.mcs044.expensetracker.entity.Employee;
import com.mcs044.expensetracker.service.EmployeeService;

@CrossOrigin
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PutMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.createEmployee(employee));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ex.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.editEmployee(employee));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeId) {
        try {
            employeeService.deleteEmployee(Long.parseLong(employeeId));
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during employee deletion: " + ex.getMessage());
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        try {
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                String errorMessage = "Employee not found with employeeId: " + employeeId;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } catch (Exception ex) {
            String errorMessage = "Error during getting employee by employeeId: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getEmployees() {
        try {
            List<Employee> employees = employeeService.getEmployees();
            if (employees != null) {
                return ResponseEntity.ok(employees);
            } else {
                String errorMessage = "Employees not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } catch (Exception ex) {
            String errorMessage = "Error during getting employee by employeeId: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
