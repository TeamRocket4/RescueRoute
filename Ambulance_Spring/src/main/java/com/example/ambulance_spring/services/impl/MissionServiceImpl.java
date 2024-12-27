package com.example.ambulance_spring.services.impl;

import com.example.ambulance_spring.dto.MissionRequest;
import com.example.ambulance_spring.entities.Hospital;
import com.example.ambulance_spring.entities.Mission;
import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.entities.enums.MissionStatus;
import com.example.ambulance_spring.repositories.HospitalRepository;
import com.example.ambulance_spring.repositories.MissionRepository;
import com.example.ambulance_spring.repositories.UserRepository;
import com.example.ambulance_spring.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mission createMission(MissionRequest request) {
        User driver = userRepository.findById(request.getDriver()).orElse(null);
        User dispatcher = userRepository.findById(request.getDispatcher()).orElse(null);
        Hospital hospital = hospitalRepository.findById(request.getHospital()).orElse(null);
        if(dispatcher==null||driver==null||hospital==null){
            return null;
        }
        Mission mission = new Mission();
        mission.setDispatcher(dispatcher);
        mission.setLongitude(request.getLongitude());
        mission.setLatitude(request.getLatitude());
        mission.setDriver(driver);
        mission.setStatus(MissionStatus.PICKUP);
        mission.setHospital(hospital);
        return missionRepository.save(mission);
    }

    @Override
    public Mission updateStatus(Long id, MissionStatus status) {
        Mission mission = missionRepository.findById(id).orElse(null);
        if(mission!=null){
            mission.setStatus(status);
            return missionRepository.save(mission);
        }
        return null;
    }
}
