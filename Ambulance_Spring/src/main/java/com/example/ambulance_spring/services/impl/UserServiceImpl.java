package com.example.ambulance_spring.services.impl;

import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.entities.enums.Role;
import com.example.ambulance_spring.entities.enums.Status;
import com.example.ambulance_spring.repositories.UserRepository;
import com.example.ambulance_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User clockin(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user==null){
            return null;
        }
        user.setStatus(Status.STANDBY);
        return userRepository.save(user);
    }

    @Override
    public User clockout(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user==null){
            return null;
        }
        user.setStatus(Status.OUTOFSERVICE);
        return userRepository.save(user);
    }

    @Override
    public List<User> getDrivers() {
        return userRepository.findByRole(Role.DRIVER);
    }
}
