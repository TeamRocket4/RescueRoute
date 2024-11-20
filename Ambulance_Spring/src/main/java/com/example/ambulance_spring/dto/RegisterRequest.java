package com.example.ambulance_spring.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class RegisterRequest {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String password;
}
