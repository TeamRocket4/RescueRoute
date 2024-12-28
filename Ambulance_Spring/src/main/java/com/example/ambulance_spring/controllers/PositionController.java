package com.example.ambulance_spring.controllers;

import com.example.ambulance_spring.dto.MissionRequest;
import com.example.ambulance_spring.dto.MissionUpdate;
import com.example.ambulance_spring.entities.Mission;
import com.example.ambulance_spring.entities.Position;
import com.example.ambulance_spring.entities.enums.MissionStatus;
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

    @MessageMapping("/driver/{id}")
    @SendTo("/map/driver/{id}")
    public Mission assignMission(@Payload MissionRequest mission, @DestinationVariable String id) {
        System.out.println("Assigning mission to driver ID: " + id);
        Mission createdMission = missionService.createMission(mission);
        return createdMission;
    }

    @MessageMapping("/mission/{id}")
    @SendTo("/map/mission/{id}")
    public Mission updateMission(@Payload MissionUpdate update, @DestinationVariable String id) {
        System.out.println("Updating mission to driver ID: " + id);
        Mission updatedMission = missionService.updateStatus(Long.parseLong(id), update.getStatus());
        return updatedMission;
    }


}