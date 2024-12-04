package com.example.ambulance_spring.controllers;


import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
