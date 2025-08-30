package com.ems.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "activity_log")
public class ActivityLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime timestamp;

	private String user; // who performed action (admin username/email)
	private String action; // CREATE / UPDATE / DELETE
	private String entityType; // Employee / Department etc.
	private String entityId; // e.g. empId
	private String status; // SUCCESS / FAILED
	private String message; // "New employee registered"
}
