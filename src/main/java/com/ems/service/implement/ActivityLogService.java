package com.ems.service.implement;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ems.model.ActivityLog;
import com.ems.repository.ActivityLogRepository;

@Service
public class ActivityLogService {
	private final ActivityLogRepository activityLogRepository;

	public ActivityLogService(ActivityLogRepository activityLogRepository) {
		this.activityLogRepository = activityLogRepository;
	}

	public void log(String user, String action, String entityType, String entityId, String status, String message) {
		ActivityLog log = ActivityLog.builder().timestamp(LocalDateTime.now()).user(user).action(action)
				.entityType(entityType).entityId(entityId).status(status).message(message).build();
		activityLogRepository.save(log);
	}

	public List<ActivityLog> getRecentLogs(Pageable pageable) {
		return activityLogRepository.findAllByOrderByTimestampDesc(pageable);
	}
}
