package com.mcs044.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
            String errorMessage = "Error during user creation: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorMessage);
        }
    }

    @CrossOrigin
    @GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try{
            return ResponseEntity.ok(userService.userLogin(username, password));
        } catch (Exception ex) {
            String errorMessage = "Error during login: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorMessage);
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
}
