package com.example.ambulance_spring.controllers;

import com.example.ambulance_spring.dto.LoginRequest;
import com.example.ambulance_spring.dto.LoginResponse;
import com.example.ambulance_spring.dto.RegisterRequest;
import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.mapper.UserMapper;
import com.example.ambulance_spring.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    // Constructor-based injection
    public AuthController(AuthService authService, UserMapper userMapper) {
        // Optionally, you can add a null-check here to fail fast
        if (authService == null) {
            throw new IllegalArgumentException("AuthService cannot be null.");
        }
        if (userMapper == null) {
            throw new IllegalArgumentException("UserMapper cannot be null.");
        }
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = userMapper.toEntity(registerRequest);

            boolean res = authService.register(user);
            if (res) {
                return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Failed to register user", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Nullable @RequestBody LoginRequest loginRequest) {
        if (loginRequest == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        LoginResponse loginResponse = authService.login(loginRequest);
        if (loginResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
