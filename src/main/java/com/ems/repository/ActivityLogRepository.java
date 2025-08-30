package com.ems.repository;



import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.model.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>{
	List<ActivityLog> findAllByOrderByTimestampDesc(Pageable pageable);

}
