package com.mcs044.expensetracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcs044.expensetracker.entity.Employee;
import com.mcs044.expensetracker.entity.Department;
import com.mcs044.expensetracker.repository.EmployeeRepository;
import com.mcs044.expensetracker.repository.DepartmentRepository;
import com.mcs044.expensetracker.utility.EmailUtility;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private EmailUtility emailUtility;

	public Employee createEmployee(Employee employee) throws Exception {
		String name = employee.getName(),
				emailAddress = employee.getEmailAddress();
		Long phoneNumber = employee.getPhoneNumber();
		int departmentId = 0;
		if (employee.getId() != null && !employee.getId().equals(0L)) { // editEmployeeCall
			departmentId = employee.getDepartment().getId().intValue();
		} else {
			departmentId = employee.getDepartmentIdInt().intValue();
		}
		switch(departmentId) {
			case 1: {
				Department department = new Department();
				department.setId(1L);
				department.setName("Management");
				departmentRepository.save(department);
				employee.setDepartment(department);
				break;
			}
			case 2: {
				Department department = new Department();
				department.setId(2L);
				department.setName("Operations");
				departmentRepository.save(department);
				employee.setDepartment(department);
				break;
			}
		}

		if (!emailUtility.isValidEmail(emailAddress))
		throw new Exception("Invalid Email ID. Please try with a different email");

		if (employee.getId() != null && !employee.getId().equals(0L)) { // editEmployeeCall
			Employee editedEmployee = getEmployeeById(employee.getId());
			if (!editedEmployee.getEmailAddress().equals(employee.getEmailAddress()) && getEmployeeByEmailAddress(emailAddress) != null)
				throw new Exception("Email is already registered. Please try with a different email");
			editedEmployee.setName(name);
			editedEmployee.setPhoneNumber(phoneNumber);
			editedEmployee.setEmailAddress(emailAddress);
			editedEmployee.setDepartment(employee.getDepartment());
			return employeeRepository.save(editedEmployee);
		}
		
		if (getEmployeeByEmailAddress(emailAddress) != null)
			throw new Exception(
					"Email is already registered. Please try with a different email");

		Employee createdEmployee = new Employee();
		createdEmployee.setName(name);
		createdEmployee.setPhoneNumber(phoneNumber);
		createdEmployee.setEmailAddress(emailAddress);
		createdEmployee.setDepartment(employee.getDepartment());

		Employee result = employeeRepository.save(createdEmployee);
		emailUtility.sendMail(name, emailAddress, "Employee registered", "You are a registered employee under " + employee.getDepartment().getName() + " department.");
		return result;
    }

	public Employee editEmployee(Employee employee) throws Exception {
		String name = employee.getName(),
				emailAddress = employee.getEmailAddress();
		Long phoneNumber = employee.getPhoneNumber();

		if (!emailUtility.isValidEmail(emailAddress))
		throw new Exception("Invalid Email ID. Please try with a different email");

		switch(employee.getDepartment().getId().intValue()) {
			case 1: {
				Department department = new Department();
				department.setId(1L);
				department.setName("Management");
				departmentRepository.save(department);
				employee.setDepartment(department);
				break;
			}
			case 2: {
				Department department = new Department();
				department.setId(2L);
				department.setName("Operations");
				departmentRepository.save(department);
				employee.setDepartment(department);
				break;
			}
		}

		if (employee.getId() != null && !employee.getId().equals(0L)) { // editEmployeeCall
			Employee editedEmployee = getEmployeeById(employee.getId());
			if (!editedEmployee.getEmailAddress().equals(employee.getEmailAddress()) && getEmployeeByEmailAddress(emailAddress) != null)
				throw new Exception("Email is already registered. Please try with a different email");
			editedEmployee.setName(name);
			editedEmployee.setPhoneNumber(phoneNumber);
			editedEmployee.setEmailAddress(emailAddress);
			editedEmployee.setDepartment(employee.getDepartment());
			return employeeRepository.save(editedEmployee);
		}

		if (getEmployeeByEmailAddress(emailAddress) != null)
			throw new Exception(
					"Email is already registered. Please try with a different email");

		Employee createdEmployee = new Employee();
		createdEmployee.setName(name);
		createdEmployee.setPhoneNumber(phoneNumber);
		createdEmployee.setEmailAddress(emailAddress);
		createdEmployee.setDepartment(employee.getDepartment());

		Employee result = employeeRepository.save(createdEmployee);
		return result;
    }
    
	public void deleteEmployee(Long userId) {
        employeeRepository.delete(employeeRepository.findById(userId).get());
	}

	public Employee getEmployeeByEmailAddress(String username) {
		return employeeRepository.findByEmailAddress(username);
	}

	public boolean isValidEmail(String email) {
		return emailUtility.isValidEmail(email);
	}

	public Employee getEmployeeById(Long userId) {
		return employeeRepository.findById(userId).orElse(null);
	}

	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

}