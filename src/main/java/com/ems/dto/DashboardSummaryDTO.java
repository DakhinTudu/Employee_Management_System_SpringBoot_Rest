package com.ems.dto;

import java.util.List;
import java.util.Map;

import com.ems.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummaryDTO {
	private long totalEmployees;
	  private long activeEmployees;
	  private long inactiveEmployees;
	  private long newThisMonth;
	  private long terminationsThisMonth;
	  private Map<String, Long> byDepartment;
	  private List<String> birthdaysThisMonth;
}
