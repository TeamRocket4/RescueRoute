package com.example.ambulance_spring.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionRequest {

    private double latitude;

    private double longitude;

    private Long driver;

    private Long dispatcher;

    private Long hospital;
}
