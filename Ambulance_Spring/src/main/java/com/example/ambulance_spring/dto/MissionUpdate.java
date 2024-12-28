package com.example.ambulance_spring.dto;

import com.example.ambulance_spring.entities.enums.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionUpdate {
    Long id;
    MissionStatus status;
}
