package com.mcs044.expensetracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.entity.MonthEnum;
import com.mcs044.expensetracker.entity.Report;

/*
 * Create a repository interface that extends JpaRepository to interact with the report entity in the database.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    public List<Report> findByConsumer(Consumer consumer);
    public Optional<Report> findByConsumerAndMonth(Consumer consumer, MonthEnum month);
    public Report findByConsumerIdAndMonth(Long consumerId, MonthEnum month);
}
