package com.ems.dto;


import com.ems.enums.Department;
import com.ems.enums.Gender;
import com.ems.enums.Status;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateDTO {
	private String name;
    @Email
    private String email;
   
	private Department dept;
	private Status status;
	private Gender gender;
    private String address;
    private Float yoe;
    private Float salary;
    private String reportingManager;

}