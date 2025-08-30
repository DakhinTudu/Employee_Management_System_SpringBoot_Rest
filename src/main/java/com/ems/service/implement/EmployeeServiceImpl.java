package com.ems.service.implement;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ems.dto.EmployeeRegisterDTO;
import com.ems.dto.EmployeeUpdateDTO;
import com.ems.mapper.EmployeeMapper;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	// DI
	private final EmployeeRepository employeeRepo;
	private final EmployeeMapper empMapper;
	private final ActivityLogService activityLogService;

	// Constructor DI
	public EmployeeServiceImpl(EmployeeRepository employeeRepo, EmployeeMapper empMapper,
			ActivityLogService activityLogService) {
		this.employeeRepo = employeeRepo;
		this.empMapper = empMapper;
		this.activityLogService = activityLogService;
	}

	// get all employees
	@Override
	public Page<Employee> getEmployees(Pageable pageable) {

		return employeeRepo.findAll(pageable);
	}

	// get employee empId
	@Override
	public Employee getEmployeeByEmpId(String empId) {
		return employeeRepo.findByEmpId(empId)
				.orElseThrow(() -> new RuntimeException("Employee not found with empId" + empId));

	}

	// get employee by email
	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Employee not found with email" + email));

	}

	// get employee by name
	@Override
	public List<Employee> getEmployeeByName(String name) {
		List<Employee> employees = employeeRepo.findByName(name);
		if (employees.isEmpty()) {
			throw new RuntimeException("Employee not fa ound with name" + name);
		}
		return employees;
	}

	// update employee
	@Override
	public Employee updateEmployee(String empId, EmployeeUpdateDTO dto) {
		Employee existingEmp = this.getEmployeeByEmpId(empId);

		// check the fields before update
		if (dto.getName() != null)
			existingEmp.setName(dto.getName());
		if (dto.getEmail() != null)
			existingEmp.setEmail(dto.getEmail());
		if (dto.getDept() != null)
			existingEmp.setDept(dto.getDept());
		if (dto.getAddress() != null)
			existingEmp.setAddress(dto.getAddress());
		if (dto.getYoe() != null)
			existingEmp.setYoe(dto.getYoe());
		if (dto.getSalary() != null)
			existingEmp.setSalary(dto.getSalary());
		if (dto.getReportingManager() != null)
			existingEmp.setReportingManager(dto.getReportingManager());

		//activity log
		activityLogService.log(
                "ADMIN", 
                "UPDATE", 
                "Employee", 
                empId, 
                "SUCCESS", 
                "Employee upadated"
        );
		return existingEmp;
	}

	// delete employee by empId
	@Override
	public void deleteEmployeeByEmpId(String empId) {
		Employee employee = this.getEmployeeByEmpId(empId);
		employeeRepo.delete(employee);

		//activity log
		activityLogService.log(
                "ADMIN", 
                "DELETE", 
                "Employee", 
                empId, 
                "SUCCESS", 
                "Employee deleted"
        );
	}

	// register new employee
	@Override
	public Employee registerEmployee(EmployeeRegisterDTO employeeDto) {
		// Save
		Employee saved = employeeRepo.save(empMapper.toEntity(employeeDto));
		// Generate business empId
		saved.setEmpId("EMP" + String.format("%05d", saved.getId()));

		//activity log 
		activityLogService.log(
                "ADMIN", 
                "CREATE", 
                "Employee", 
                saved.getEmpId(), 
                "SUCCESS", 
                "New employee registered"
        );

		// Save again with empId
		return employeeRepo.save(saved);
	}

}
