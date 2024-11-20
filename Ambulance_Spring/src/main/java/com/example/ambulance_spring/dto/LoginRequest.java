package com.example.ambulance_spring.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
