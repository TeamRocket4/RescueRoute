package com.example.ambulance_spring.controllers;

import com.example.ambulance_spring.dto.MissionRequest;
import com.example.ambulance_spring.entities.Mission;
import com.example.ambulance_spring.entities.Position;
import com.example.ambulance_spring.entities.enums.MissionStatus;
import com.example.ambulance_spring.services.MissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PositionControllerTest {

    @Mock
    private MissionService missionService;

    @InjectMocks
    private PositionController positionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void send_shouldReturnPosition() {
        // Arrange
        Position inputPosition = new Position(1L, 40.7128, -74.0060);

        // Act
        Position result = positionController.send(inputPosition);

        // Assert
        assertEquals(inputPosition.getId(), result.getId());
        assertEquals(inputPosition.getLatitude(), result.getLatitude());
        assertEquals(inputPosition.getLongitude(), result.getLongitude());
    }

    @Test
    void assignMission_shouldReturnCreatedMission() {
        // Arrange
        MissionRequest missionRequest = new MissionRequest();
        missionRequest.setLatitude(40.7128);
        missionRequest.setLongitude(-74.0060);

        Mission createdMission = new Mission();
        createdMission.setId(1L);
        createdMission.setLatitude(40.7128);
        createdMission.setLongitude(-74.0060);
        createdMission.setStatus(MissionStatus.PENDING);

        when(missionService.createMission(missionRequest)).thenReturn(createdMission);

        // Act
        Mission result = positionController.assignMission(missionRequest, "123");

        // Assert
        assertEquals(createdMission.getId(), result.getId());
        assertEquals(createdMission.getLatitude(), result.getLatitude());
        assertEquals(createdMission.getLongitude(), result.getLongitude());
        assertEquals(createdMission.getStatus(), result.getStatus());

        verify(missionService, times(1)).createMission(missionRequest);
    }
}
