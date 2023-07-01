package com.mcs044.expensetracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(ReportId.class)
public class Report {

    @Id
    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @Id
    @Enumerated(EnumType.STRING)
    private MonthEnum month;

    @Id
    private Integer year;
    
    private double budget;
    private double expenditure;
    private double saving;

}
