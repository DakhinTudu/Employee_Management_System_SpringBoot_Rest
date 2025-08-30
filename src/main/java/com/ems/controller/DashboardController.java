package com.ems.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.dto.ApiResponse;
import com.ems.dto.DashboardSummaryDTO;
import com.ems.dto.PointDTO;
import com.ems.model.ActivityLog;
import com.ems.service.implement.DashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
//@PreAuthorize("hasRole('ADMIN'")
public class DashboardController {

	private final DashboardService service;

	public DashboardController(DashboardService service) {
		super();
		this.service = service;

	}

	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<DashboardSummaryDTO>> summary() {
		return ResponseEntity.ok(new ApiResponse<>(true, "summary fetched", service.summary()));
	}

	@GetMapping("/activity")
	public ResponseEntity<ApiResponse<List<ActivityLog>>> activity(@RequestParam(defaultValue = "10") int limit) {
		return ResponseEntity.ok(new ApiResponse<>(true, "activity fetched", service.recentActivity(limit)));
	}

//	@GetMapping("/distribution/departments")
//	public ResponseEntity<ApiResponse<Map<String, Long>>> deptDistribution() {
//		return ResponseEntity.ok(new ApiResponse<>(true, "departments fetched", service.departmentDistribution());
//	}

	@GetMapping("/trends/hires")
	public ResponseEntity<ApiResponse<List<PointDTO>>> hires(@RequestParam int year) {
		return ResponseEntity.ok(new ApiResponse<>(true, "Hires fetched", service.hiresTrend(year)));
	}

	@GetMapping("/trends/terminations")
	public ResponseEntity<ApiResponse<List<PointDTO>>> terms(@RequestParam int year) {
		return ResponseEntity.ok(new ApiResponse<>(true, "terminations fetched", service.terminationsTrend(year)));
	}

//	  @GetMapping("/alerts")
//	  public ResponseEntity<List<AlertDTO>> alerts(
//	      @RequestParam(defaultValue = "30") int contractDays,
//	      @RequestParam(defaultValue = "6") int staleMonths) {
//	    return ResponseEntity.ok(service.alerts(contractDays, staleMonths));
//	  }

}
