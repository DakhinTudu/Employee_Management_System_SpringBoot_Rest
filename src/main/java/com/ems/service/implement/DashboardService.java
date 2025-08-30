package com.ems.service.implement;


import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ems.dto.DashboardSummaryDTO;
import com.ems.dto.DeptCount;
import com.ems.dto.PointDTO;
import com.ems.enums.Status;
import com.ems.model.ActivityLog;
import com.ems.model.Employee;
import com.ems.repository.ActivityLogRepository;
import com.ems.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private ActivityLogRepository activityRepo;

	// admin-dashboard summary
	@Cacheable(value = "dashSummary", key = "'v1'", unless = "#result == null")
	public DashboardSummaryDTO summary() {
		LocalDate now = LocalDate.now();
		LocalDate monthStart = now.withDayOfMonth(1);
		LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);

		long total = employeeRepo.count();
		long active = employeeRepo.countByStatus(Status.ACTIVE);
		long inactive = total - active;
		long newThisMonth = employeeRepo.countNewBetween(monthStart, monthEnd);
		long termsThisMonth = employeeRepo.countByTerminationDateBetween(monthStart, monthEnd);

		Map<String, Long> byDept = employeeRepo.countByDepartment().stream().filter(dc -> dc.getDepartment() != null)
				.collect(Collectors.toMap(DeptCount::getDepartment, DeptCount::getCount));

		// get birthdays
		int currentMonth = now.getMonthValue();
		List<String> birthdays = employeeRepo.findByBirthdayMonth(currentMonth).stream().map(Employee::getName) 
				.collect(Collectors.toList());

		return new DashboardSummaryDTO(total, active, inactive, newThisMonth, termsThisMonth, byDept, birthdays);
	}

	// recent activities log
	public List<ActivityLog> recentActivity(int limit) {
		Pageable pageable = (Pageable) PageRequest.of(0, limit); // first page, 'limit' items
		return activityRepo.findAllByOrderByTimestampDesc(pageable);
	}

	// hires trends for chart
	public List<PointDTO> hiresTrend(int year) {
		LocalDate start = LocalDate.of(year, 1, 1);
		LocalDate end = LocalDate.of(year, 12, 31);
		Map<Integer, Long> map = employeeRepo.hiresByMonth(start, end).stream()
				.collect(Collectors.toMap(r -> ((Number) r[0]).intValue(), r -> ((Number) r[1]).longValue()));

		return IntStream.rangeClosed(1, 12).mapToObj(m -> new PointDTO(m, map.getOrDefault(m, 0L))).toList();
	}

	// terminations trends for chart
	public List<PointDTO> terminationsTrend(int year) {
		LocalDate start = LocalDate.of(year, 1, 1);
		LocalDate end = LocalDate.of(year, 12, 31);
		Map<Integer, Long> map = employeeRepo.terminationsByMonth(start, end).stream()
				.collect(Collectors.toMap(r -> ((Number) r[0]).intValue(), r -> ((Number) r[1]).longValue()));

		return IntStream.rangeClosed(1, 12).mapToObj(m -> new PointDTO(m, map.getOrDefault(m, 0L))).toList();
	}

//	public List<AlertDTO> alerts(int contractDays, int staleMonths) {
//		LocalDate today = LocalDate.now();
//		List<AlertDTO> out = new ArrayList<>();
//
//		employeeRepo.contractsExpiring(today, today.plusDays(contractDays)).forEach(e -> out
//				.add(new AlertDTO("contract_expiry", e.getEmpId(), "Contract expiring soon", e.getContractEndDate())));
//
//		LocalDateTime thresh = LocalDateTime.now().minusMonths(staleMonths);
//		employeeRepo.profilesStale(thresh).forEach(e -> out.add(new AlertDTO("profile_stale", e.getEmpId(),
//				"Profile not updated in " + staleMonths + " months", null)));
//
//		return out;
//	}

}
