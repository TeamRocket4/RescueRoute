package com.example.ambulance_spring.controllers;

import com.example.ambulance_spring.entities.Position;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PositionController {
    @MessageMapping("/position")
    @SendTo("/map/positions")
    public Position send(Position position){
        System.out.println(position.getId());
        return new Position(position.getId(), position.getLatitude(), position.getLongitude());
    }
}
