package com.ems.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ems.dto.EmployeeRegisterDTO;
import com.ems.model.Employee;

@Component
public class EmployeeMapper {

	public Employee toEntity(EmployeeRegisterDTO dto) {
		Employee employee = new Employee();
		employee.setName(dto.getName());
		employee.setEmail(dto.getEmail());
		employee.setDept(dto.getDept());
		employee.setGender(dto.getGender());
		employee.setStatus(dto.getStatus());
		employee.setAddress(dto.getAddress());
		employee.setYoe(dto.getYoe());
		employee.setSalary(dto.getSalary());
		employee.setReportingManager(dto.getReportingManager());
		employee.setDob(dto.getDob());
		employee.setJoiningDate(dto.getJoiningDate() != null ? dto.getJoiningDate() : LocalDate.now());
		return employee;
	}

	public EmployeeRegisterDTO toDto(Employee entity) {
		EmployeeRegisterDTO dto = new EmployeeRegisterDTO();
		dto.setName(entity.getName());
		dto.setEmail(entity.getEmail());
		dto.setDept(entity.getDept());
		dto.setGender(entity.getGender());
		dto.setStatus(entity.getStatus());
		dto.setAddress(entity.getAddress());
		dto.setYoe(entity.getYoe());
		dto.setSalary(entity.getSalary());
		dto.setReportingManager(entity.getReportingManager());
		dto.setDob(entity.getDob());
		dto.setJoiningDate(entity.getJoiningDate());
		return dto;
	}
}
