package com.mcs044.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.service.ConsumerService;

@RestController
@RequestMapping("/api/user")
public class ConsumerController {

    @Autowired
    private ConsumerService userService;

    @CrossOrigin
    @PutMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Consumer user) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ex.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try{
            Consumer returned = userService.userLogin(username, password);
            return ResponseEntity.ok(returned);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ex.getMessage());
        }
	}

    @CrossOrigin
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword, 
                @RequestParam("newPassword") String newPassword) {
        try {
            String resetStatus = userService.passwordReset(username, oldPassword, newPassword);
            return ResponseEntity.ok(resetStatus);
        } catch (Exception ex) {
            String errorMessage = "Error during password reset: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorMessage);
        }
    }

    @CrossOrigin
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        try {
            Consumer user = userService.getUserById(userId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                String errorMessage = "User not found with userId: " + userId;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } catch (Exception ex) {
            String errorMessage = "Error during getting user by userId: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
