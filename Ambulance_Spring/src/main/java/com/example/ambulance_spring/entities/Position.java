package com.example.ambulance_spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    private Long id;
    private double latitude;
    private double longitude;
}
