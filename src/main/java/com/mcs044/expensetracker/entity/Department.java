package com.mcs044.expensetracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Department {

    @Id
	private Long id;

	private String name;

}
