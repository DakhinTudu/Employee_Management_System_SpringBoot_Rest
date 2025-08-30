package com.ems.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.dto.ApiResponse;
import com.ems.dto.EmployeeRegisterDTO;
import com.ems.dto.EmployeeUpdateDTO;
import com.ems.model.Employee;
import com.ems.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService empService;

	public EmployeeController(EmployeeService empService) {
		this.empService = empService;
	}

	// get all employees
	@GetMapping
	public ResponseEntity<ApiResponse<Page<Employee>>> getEmployees(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<Employee> employees = empService.getEmployees(pageable);
		return ResponseEntity.ok(new ApiResponse<>(true, "All employees fetched", employees));
	}

	// get employee by empId

	@GetMapping("/empId/{empId}")
	public ResponseEntity<ApiResponse<?>> getEmployeeByEmpId(@PathVariable("empId") String empId) {

		try {

			Employee employee = empService.getEmployeeByEmpId(empId);
			return ResponseEntity.ok(new ApiResponse<Employee>(true, "Employee fetched successfully", employee));

		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<Employee>(false, "Employee not found with empId" + empId, null));
		}

	}

	// get employee by name

	@GetMapping("/name/{name}")
	public ResponseEntity<ApiResponse<List<Employee>>> getEmployeeByName(@PathVariable String name) {
		try {
			List<Employee> employee = empService.getEmployeeByName(name);
			return ResponseEntity.ok(new ApiResponse<>(true, "Employee fetched successful;ly", employee));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>(true, "Employee fetched successful;ly", null));
		}
	}

	// get employee by email

	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponse<Employee>> getEmployeeByEmail(@PathVariable String email) {
		try {
			Employee employee = empService.getEmployeeByEmail(email);
			return ResponseEntity.ok(new ApiResponse<>(true, "Employee fetched successful;ly", employee));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>(true, "Employee fetched successful;ly", null));
		}
	}

	// register new employee
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<?>> registerEmployee(@RequestBody EmployeeRegisterDTO empDto) {
		Employee employee = empService.registerEmployee(empDto);
		return ResponseEntity.ok(new ApiResponse<>(true, "Employee Registered Successfully", employee));
	}

	// update employee
	@PutMapping("update/{empId}")
	public ResponseEntity<ApiResponse<?>> updateEmployee(@PathVariable String empId,
			@RequestBody EmployeeUpdateDTO empDto) {
		try {
			Employee employee = empService.updateEmployee(empId, empDto);
			return ResponseEntity.ok(new ApiResponse<>(true, "Employee update successfully", employee));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>(false, "Employee not found with empId" + empId, null));
		}

	}

	// delete employee
	@DeleteMapping("/delete/{empId}")
	public ResponseEntity<ApiResponse<?>> deleteEmployee(@PathVariable String empId) {
		try {
			empService.deleteEmployeeByEmpId(empId);
			return ResponseEntity.ok(new ApiResponse<>(true, "Employee Deleted", null));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>(false, "Employee not found with " + empId, null));
		}
	}

}
