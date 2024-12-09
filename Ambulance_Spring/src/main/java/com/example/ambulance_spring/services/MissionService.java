package com.example.ambulance_spring.services;

import com.example.ambulance_spring.dto.MissionRequest;
import com.example.ambulance_spring.entities.Mission;

public interface MissionService {
    Mission createMission(MissionRequest request);
}
