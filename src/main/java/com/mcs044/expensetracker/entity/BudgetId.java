package com.mcs044.expensetracker.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BudgetId implements Serializable {

    private Long consumer;
    private MonthEnum month;

}