package com.ems.model;

import java.time.LocalDate;

import com.ems.enums.Department;
import com.ems.enums.Gender;
import com.ems.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true) // should be update-able because later EMP ID will be generated
	private String empId;
	@Column(nullable = false)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(length = 10) // length enough for "FEMALE"
	private Gender gender;
	@Column(unique = true)
	@Email
	private String email;
	@Enumerated(EnumType.STRING)
	private Department dept;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(nullable = false)
	private String address;
	private Float yoe;
	private Float salary;
	private String reportingManager;
	private LocalDate joiningDate;
	private LocalDate terminationDate;
	@Column(nullable = false)
	
	private LocalDate dob;

}
