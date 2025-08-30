package com.ems.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlertDTO {
	private String type; // contract_expiry, profile_stale
	private String empId;
	private String message;
	private LocalDate dueDate; // or lastUpdated
}
