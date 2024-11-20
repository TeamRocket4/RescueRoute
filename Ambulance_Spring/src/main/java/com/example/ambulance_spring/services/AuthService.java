package com.example.ambulance_spring.services;

import com.example.ambulance_spring.dto.LoginRequest;
import com.example.ambulance_spring.dto.LoginResponse;
import com.example.ambulance_spring.entities.User;

public interface AuthService {
    boolean register(User user);
    LoginResponse login(LoginRequest loginRequest);
}
