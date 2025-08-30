package com.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ems.dto.DeptCount;
import com.ems.enums.Status;
import com.ems.model.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	// basic operation for Employee service
	Optional<Employee> findByEmpId(String empId);

	List<Employee> findByName(String name);

	Optional<Employee> findByEmail(String email);

	// operations for admin-dashboard

	// counts total employees
	long count();

	// count employees by status
	long countByStatus(Status status);

	// count new employees/ between date
	@Query("select count(e) from Employee e where e.joiningDate between :start and :end")
	long countNewBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

	// count terminated employees between month
	long countByTerminationDateBetween(LocalDate start, LocalDate end);

	// count number of employees by department
	@Query("SELECT e.dept AS dept, COUNT(e) AS count FROM Employee e GROUP BY e.dept")
	List<DeptCount> countByDepartment();

	// hiresByMonth trends for preparing chart
	@Query("SELECT function('month', e.joiningDate), COUNT(e) " + "FROM Employee e "
			+ "WHERE e.joiningDate BETWEEN :start AND :end " + "GROUP BY function('month', e.joiningDate)")
	List<Object[]> hiresByMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);

	// terminationsByMonth of employees for preparing chart
	@Query("SELECT MONTH(e.terminationDate), COUNT(e) " + "FROM Employee e "
			+ "WHERE e.terminationDate BETWEEN :start AND :end " + "GROUP BY MONTH(e.terminationDate)")
	List<Object[]> terminationsByMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);
	
	// findBirthDayMonth
	@Query("SELECT e FROM Employee e WHERE FUNCTION('month', e.dob) = :month")
	List<Employee> findByBirthdayMonth(@Param("month") int month);


//	@Query("SELECT e FROM Employee e WHERE e.contractEndDate BETWEEN :startDate AND :endDate")
//	List<Employee> contractsExpiring(@Param("startDate") LocalDate startDate,
//	                                 @Param("endDate") LocalDate endDate);
//
//
//	@Query("select e from Employee e where e.lastUpdatedAt < :threshold")
//	List<Employee> profilesStale(@Param("threshold") LocalDateTime threshold);

}
