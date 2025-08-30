package com.ems.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ems.dto.EmployeeRegisterDTO;
import com.ems.dto.EmployeeUpdateDTO;
import com.ems.model.Employee;

public interface EmployeeService {
	Page<Employee> getEmployees(Pageable pageable);

	Employee getEmployeeByEmpId(String empId);

	Employee getEmployeeByEmail(String email);

	List<Employee> getEmployeeByName(String name);

	Employee updateEmployee(String empId, EmployeeUpdateDTO empDto);

	void deleteEmployeeByEmpId(String empId);

	Employee registerEmployee(EmployeeRegisterDTO employeeDto);

}
