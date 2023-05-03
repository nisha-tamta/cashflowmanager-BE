package com.mcs044.expensetracker.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

/*
 * Create an Expense class that represents an expense entity.
 * This class will be used to map the data to the database.
*/
@Getter
@Setter
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_id_seq")
    @SequenceGenerator(name = "expense_id_seq", sequenceName = "expense_id_seq", allocationSize = 1)
    private Long id;

    private double amount;
    private LocalDate date;
    private String description;
    private String category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

}