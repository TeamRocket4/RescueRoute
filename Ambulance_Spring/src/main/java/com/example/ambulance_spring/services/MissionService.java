package com.example.ambulance_spring.services;

import com.example.ambulance_spring.dto.MissionRequest;
import com.example.ambulance_spring.entities.Mission;
import com.example.ambulance_spring.entities.enums.MissionStatus;

public interface MissionService {
    Mission createMission(MissionRequest request);

    Mission updateStatus(Long id, MissionStatus status);
}
