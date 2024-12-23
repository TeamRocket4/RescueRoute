package com.example.ambulance_spring.controllers;


import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/clockout/{id}")
    private ResponseEntity<String> clockout(@PathVariable Long id){
        return ResponseEntity.status(200).body(userService.clockout(id).getStatus().toString());
    }

    @PostMapping("/clockin/{id}")
    private ResponseEntity<String> clockin(@PathVariable Long id){
        return ResponseEntity.status(200).body(userService.clockin(id).getStatus().toString());
    }

    @GetMapping("/drivers")
    private ResponseEntity<List<User>> getDrivers(){
        List<User> drivers = userService.getDrivers();
        return ResponseEntity.status(200).body(drivers);
    }
}
