package com.ems.service;


public interface AuthService {

//	UserDto registerUser(SignupRequest request);
//    String login(LoginRequest request);
    String refreshToken(String refreshToken);

}
