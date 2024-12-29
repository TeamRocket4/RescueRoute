package com.example.ambulance_spring.controllers;

import com.example.ambulance_spring.dto.MissionRequest;
import com.example.ambulance_spring.dto.MissionUpdate;
import com.example.ambulance_spring.entities.Mission;
import com.example.ambulance_spring.entities.Position;
import com.example.ambulance_spring.services.MissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class PositionController {

    private final MissionService missionService;

    // Constructor injection
    public PositionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @MessageMapping("/position")
    @SendTo("/map/positions")
    public Position send(Position position) {
        log.info("Position received: {}", position.getId());
        return new Position(position.getId(), position.getLatitude(), position.getLongitude());
    }

    @MessageMapping("/driver/{id}")
    @SendTo("/map/driver/{id}")
    public Mission assignMission(@Payload MissionRequest mission, @DestinationVariable String id) {
        log.info("Assigning mission to driver ID: {}", id);
        // Immediately return instead of using a temporary variable
        return missionService.createMission(mission);
    }

    @MessageMapping("/mission/{id}")
    @SendTo("/map/mission/{id}")
    public Mission updateMission(@Payload MissionUpdate update, @DestinationVariable String id) {
        log.info("Updating mission to driver ID: {}", id);
        // Immediately return instead of using a temporary variable
        return missionService.updateStatus(Long.parseLong(id), update.getStatus());
    }

}
