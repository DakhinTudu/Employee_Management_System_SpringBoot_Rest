package com.ems.service;

import java.util.List;

import com.ems.dto.UserDto;
import com.ems.model.Users;

public interface UserService {

	Users saveUser(String username, String password);
	String verifyUser(Users user);
	Users updateUser(Users user);
	String deleteUser(String username);
	UserDto getUserByUsername(String username);
	List<UserDto> getAllUsers();
}
