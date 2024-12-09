package com.example.ambulance_spring.controllers;

import com.example.ambulance_spring.dto.MissionRequest;
import com.example.ambulance_spring.entities.Mission;
import com.example.ambulance_spring.entities.Position;
import com.example.ambulance_spring.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PositionController {

    @Autowired
    private MissionService missionService;

    @MessageMapping("/position")
    @SendTo("/map/positions")
    public Position send(Position position) {
        System.out.println("Position received: " + position.getId());
        return new Position(position.getId(), position.getLatitude(), position.getLongitude());
    }

    @MessageMapping("/position/{id}")
    @SendTo("/map/driver/{id}")
    public Mission assignMission(@Payload MissionRequest mission, @DestinationVariable String id) {
        System.out.println("Assigning mission to driver ID: " + id);
        Mission createdMission = missionService.createMission(mission);
        return createdMission;
    }
}