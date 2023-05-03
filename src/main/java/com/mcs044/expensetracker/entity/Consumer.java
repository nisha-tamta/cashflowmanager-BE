package com.mcs044.expensetracker.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Details of and credential storage for the Consumer
 * 
 * @author	Nisha
 * @since	2023-01-01
 *
 */
@Getter
@Setter
@Entity
public class Consumer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Long phoneNumber;
	private String emailAddress;
	private byte[] salt;
	private double defaultBudget = 50000L;

	@OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Budget> budgets = new ArrayList<>();

	@OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL)
	private List<Expense> expenses;
}
