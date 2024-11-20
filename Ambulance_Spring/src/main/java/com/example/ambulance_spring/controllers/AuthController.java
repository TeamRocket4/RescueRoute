package com.example.ambulance_spring.controllers;

import com.example.ambulance_spring.dto.LoginRequest;
import com.example.ambulance_spring.dto.LoginResponse;
import com.example.ambulance_spring.dto.RegisterRequest;
import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.mapper.UserMapper;
import com.example.ambulance_spring.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = userMapper.toEntity(registerRequest);

            boolean res = authService.register(user);
            if(res){
                return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Failed to register user", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse= authService.login(loginRequest);
        if(loginResponse==null){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
