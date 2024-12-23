package com.example.ambulance_spring.services;

import com.example.ambulance_spring.entities.User;

import java.util.List;

public interface UserService {
    User clockin(Long id);
    User clockout(Long id);

    List<User> getDrivers();
}
