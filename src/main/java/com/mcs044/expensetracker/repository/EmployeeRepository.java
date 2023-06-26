package com.mcs044.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcs044.expensetracker.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	public Employee findByEmailAddress(String emailAddress);
	public Employee findByName(String name);
}
