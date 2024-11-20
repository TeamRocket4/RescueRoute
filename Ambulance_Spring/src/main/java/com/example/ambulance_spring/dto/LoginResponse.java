package com.example.ambulance_spring.dto;

import lombok.Data;

@Data
public class LoginResponse {
    Long id;
    String firstname;
    String lastname;
    String email;
    String authToken;
}
