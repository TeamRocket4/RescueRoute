package com.example.ambulance_spring.entities.enums;

public enum MissionStatus {
    PENDING,       // Mission is created but not yet assigned.
    ASSIGNED,      // Mission is assigned to a driver.
    ACCEPTED,      // Driver has accepted the mission.
    IN_PROGRESS,   // Mission is currently being executed (e.g., en route to the location).
    COMPLETED,     // Mission is successfully completed.
    CANCELLED      // Mission has been cancelled.
}
