package com.ems.service.implement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ems.dto.UserDto;
import com.ems.exception.UserAlreadyExistException;
import com.ems.model.Users;
import com.ems.repository.UserRepository;
import com.ems.security.JwtUtil;

@Service
public class UserServiceImpl implements com.ems.service.UserService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepo;
	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;

	public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepo,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Users saveUser(String username, String password) {
		UserDto existingUser = this.getUserByUsername(username);
		if (existingUser != null) {
			throw new UserAlreadyExistException("Username '" + username + "' already exists");
		}

		Users user = new Users();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		return userRepo.save(user);
	}

	@Override
	public String verifyUser(Users user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtUtil.generateToken(user.getUsername());
		}
		return "User verification failed";
	}

	@Override
	public Users updateUser(Users user) {
		Optional<Users> existingUser = userRepo.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			Users dbUser = existingUser.get();
			if (user.getPassword() != null && !user.getPassword().isEmpty()) {
				dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
			}

			return userRepo.save(dbUser);
		}
		return null;
	}

	@Override
	public String deleteUser(String username) {
		Optional<Users> user = userRepo.findByUsername(username);
		if (user.isPresent()) {
			userRepo.delete(user.get());
			return "User deleted successfully: " + username;
		}
		return "User not found: " + username;
	}

	@Override
	public UserDto getUserByUsername(String username) {
		return userRepo.findByUsername(username).map(user -> new UserDto(user.getId(), user.getUsername()))
				.orElse(null);

	}

	@Override
	public List<UserDto> getAllUsers() {

		return userRepo.findAll().stream().map(users -> {
			UserDto dto = new UserDto();
			dto.setId(users.getId());
			dto.setUsername(users.getUsername());
			return dto;
		}).collect(Collectors.toList());
	}

}
