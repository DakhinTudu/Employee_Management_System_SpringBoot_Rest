package com.ems.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.dto.ApiResponse;
import com.ems.model.Users;
import com.ems.service.AuthService;
import com.ems.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Auth", description = "Auth management APIs")
public class AuthController {

	private UserService userService;
	private AuthService authService;

	public AuthController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/signup")
	@Operation(summary = "Sign up", description = "sign up as new user(Admin)")
	public ResponseEntity<ApiResponse<Users>> signup(@RequestBody Users user) {
		Users savedUser = userService.saveUser(user.getUsername(), user.getPassword());
		return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", savedUser));
	}

	@PostMapping("/login")
	@Operation(summary = "Login", description = "Log in into the system as admin")
	public ResponseEntity<ApiResponse<String>> login(@RequestBody Users user) {
		String token = userService.verifyUser(user);
		if (token.startsWith("User verification failed")) {
			return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid credentials", null));
		}
		return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", token));
	}

//	@PostMapping("/refresh")
//	public ResponseEntity<ApiResponse<String>> refreshToken(@RequestParam String refreshToken) {
//		String newToken = authService.refreshToken(refreshToken);
//		return ResponseEntity.ok(new ApiResponse<>(true, "Token refreshed", newToken));
//	}
//
//	@PostMapping("/logout")
//	public ResponseEntity<ApiResponse<Void>> logout() {
//		// Optional: Invalidate refresh token
//		return ResponseEntity.ok(new ApiResponse<>(true, "Logout successful", null));
//	}
}
