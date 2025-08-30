package com.ems.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.ems.model.Users;
import com.ems.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepository userRepo;

	public MyUserDetailsService(UserRepository repo) {
		this.userRepo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword()) // hashed password
				.build();
	}
}
