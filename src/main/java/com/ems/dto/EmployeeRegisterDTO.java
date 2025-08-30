package com.ems.dto;

import java.time.LocalDate;

import com.ems.enums.Department;
import com.ems.enums.Gender;
import com.ems.enums.Status;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRegisterDTO {
	@NotBlank(message = "Name is required")
	private String name;

	@Email
	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Department is required")
	private Department dept;
	private Status status;
	@NotBlank(message = "Gender is required")
	private Gender gender;

	@NotBlank(message = "Address is required")
	private String address;

	@NotNull(message = "Date of Birth is required")
	private LocalDate dob;

	private Float yoe;
	private Float salary;
	private String reportingManager;
	private LocalDate joiningDate;
}
