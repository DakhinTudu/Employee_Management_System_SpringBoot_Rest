package com.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointDTO {
	private int month; // 1–12
	private long count;
}
