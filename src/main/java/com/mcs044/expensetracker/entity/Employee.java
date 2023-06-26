package com.mcs044.expensetracker.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Details of the Employee
 * 
 * @author	Nisha
 * @since	2023-01-01
 *
 */
@Getter
@Setter
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Long phoneNumber;

	@Column(unique=true)
	private String emailAddress;

    @ManyToOne
	@JoinColumn(name = "department_id")
    private Department department;

	@Nullable
	private Long departmentIdInt;

}
