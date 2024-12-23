package com.example.ambulance_spring.entities;


import com.example.ambulance_spring.entities.enums.MissionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "missions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private double latitude;

    private double longitude;


    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "dispatcher_id")
    private User dispatcher;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;


    @Enumerated(EnumType.STRING)
    private MissionStatus status;
}
