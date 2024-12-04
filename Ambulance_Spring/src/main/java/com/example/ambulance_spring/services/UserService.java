package com.example.ambulance_spring.services;

import com.example.ambulance_spring.entities.User;

public interface UserService {
    User clockin(Long id);
    User clockout(Long id);
}
