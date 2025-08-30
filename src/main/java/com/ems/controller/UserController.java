package com.ems.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.dto.ApiResponse;
import com.ems.dto.UserDto;
import com.ems.model.Users;
import com.ems.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/{username}")
	public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable String username) {
		UserDto user = userService.getUserByUsername(username);
		if (user != null)
			return ResponseEntity.ok(new ApiResponse<>(true, "User fetched successfully", user));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "User Not Found", null));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
		List<UserDto> users = userService.getAllUsers();
		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "User List Empty", null));
		}
		return ResponseEntity.ok(new ApiResponse<>(true, "All users fetched", users));
	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse<Users>> updateUser(@RequestBody Users user) {
		Users updatedUser = userService.updateUser(user);
		if (updatedUser == null) {
			return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null));
		}
		return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updatedUser));
	}

	@DeleteMapping("/delete/{username}")
	public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String username) {
		String result = userService.deleteUser(username);
		if (result.startsWith("User not found")) {
			return ResponseEntity.status(404).body(new ApiResponse<>(false, result, null));
		}
		return ResponseEntity.ok(new ApiResponse<>(true, result, null));
	}

}
