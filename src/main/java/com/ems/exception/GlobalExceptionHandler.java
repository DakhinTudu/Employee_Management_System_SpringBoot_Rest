package com.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ems.dto.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ApiResponse<String>> handleUserAlreadyExistException(UserAlreadyExistException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(false, ex.getMessage(), null)); // using
																													// exception																											// message
	}

	// Handle all other exceptions (fallback)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return new ResponseEntity<>("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Handle JWT expiration
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ApiResponse<?>> handleExpiredJwt(ExpiredJwtException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponse<>(false, "Session expired. Please log in again.", null));
	}
}
