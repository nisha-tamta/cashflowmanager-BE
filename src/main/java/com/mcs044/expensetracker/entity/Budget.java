package com.mcs044.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(BudgetId.class)
public class Budget {

    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @Id
    @Enumerated(EnumType.STRING)
    private MonthEnum month;

    private double amount;

}