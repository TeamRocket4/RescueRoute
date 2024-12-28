package com.example.ambulance_spring.services;

import com.example.ambulance_spring.dto.MissionRequest;
import com.example.ambulance_spring.entities.Hospital;
import com.example.ambulance_spring.entities.Mission;
import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.entities.enums.MissionStatus;
import com.example.ambulance_spring.repositories.HospitalRepository;
import com.example.ambulance_spring.repositories.MissionRepository;
import com.example.ambulance_spring.repositories.UserRepository;
import com.example.ambulance_spring.services.impl.MissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class MissionServiceTest {

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MissionServiceImpl missionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMission_Success() {
        // Arrange
        MissionRequest request = new MissionRequest();
        request.setDriver(1L);
        request.setDispatcher(2L);
        request.setHospital(3L);
        request.setLatitude(45.0);
        request.setLongitude(-93.0);

        User driver = new User();
        driver.setId(1L);
        User dispatcher = new User();
        dispatcher.setId(2L);
        Hospital hospital = new Hospital();
        hospital.setId(3L);

        Mission mission = new Mission();
        mission.setId(1L);
        mission.setDriver(driver);
        mission.setDispatcher(dispatcher);
        mission.setHospital(hospital);
        mission.setLatitude(45.0);
        mission.setLongitude(-93.0);
        mission.setStatus(MissionStatus.PICKUP);

        when(userRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(userRepository.findById(2L)).thenReturn(Optional.of(dispatcher));
        when(hospitalRepository.findById(3L)).thenReturn(Optional.of(hospital));
        when(missionRepository.save(any(Mission.class))).thenReturn(mission);

        // Act
        Mission result = missionService.createMission(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getDriver().getId());
        assertEquals(2L, result.getDispatcher().getId());
        assertEquals(3L, result.getHospital().getId());
        assertEquals(45.0, result.getLatitude());
        assertEquals(-93.0, result.getLongitude());
        assertEquals(MissionStatus.PICKUP, result.getStatus());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
        verify(hospitalRepository, times(1)).findById(3L);
        verify(missionRepository, times(1)).save(any(Mission.class));
    }

    @Test
    void createMission_Failure_MissingEntities() {
        // Arrange
        MissionRequest request = new MissionRequest();
        request.setDriver(1L);
        request.setDispatcher(2L);
        request.setHospital(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        when(hospitalRepository.findById(3L)).thenReturn(Optional.empty());

        // Act
        Mission result = missionService.createMission(request);

        // Assert
        assertNull(result);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
        verify(hospitalRepository, times(1)).findById(3L);
        verify(missionRepository, times(0)).save(any(Mission.class));
    }


    @Test
    void createMission_Failure_DriverNotFound() {
        MissionRequest request = new MissionRequest();
        request.setDriver(1L);
        request.setDispatcher(2L);
        request.setHospital(3L);
        request.setLatitude(45.0);
        request.setLongitude(-93.0);

        User dispatcher = new User();
        dispatcher.setId(2L);
        Hospital hospital = new Hospital();
        hospital.setId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(2L)).thenReturn(Optional.of(dispatcher));
        when(hospitalRepository.findById(3L)).thenReturn(Optional.of(hospital));

        Mission result = missionService.createMission(request);

        assertNull(result);
        verify(missionRepository, never()).save(any(Mission.class));
    }

    @Test
    void createMission_Failure_DispatcherNotFound() {
        MissionRequest request = new MissionRequest();
        request.setDriver(1L);
        request.setDispatcher(2L);
        request.setHospital(3L);
        request.setLatitude(45.0);
        request.setLongitude(-93.0);

        User driver = new User();
        driver.setId(1L);
        Hospital hospital = new Hospital();
        hospital.setId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        when(hospitalRepository.findById(3L)).thenReturn(Optional.of(hospital));

        Mission result = missionService.createMission(request);

        assertNull(result);
        verify(missionRepository, never()).save(any(Mission.class));
    }

    @Test
    void createMission_Failure_HospitalNotFound() {
        MissionRequest request = new MissionRequest();
        request.setDriver(1L);
        request.setDispatcher(2L);
        request.setHospital(3L);
        request.setLatitude(45.0);
        request.setLongitude(-93.0);

        User driver = new User();
        driver.setId(1L);
        User dispatcher = new User();
        dispatcher.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(userRepository.findById(2L)).thenReturn(Optional.of(dispatcher));
        when(hospitalRepository.findById(3L)).thenReturn(Optional.empty());

        Mission result = missionService.createMission(request);

        assertNull(result);
        verify(missionRepository, never()).save(any(Mission.class));
    }

    @Test
    void createMission_ValidatesAllFields() {
        MissionRequest request = new MissionRequest();
        request.setDriver(1L);
        request.setDispatcher(2L);
        request.setHospital(3L);
        request.setLatitude(45.0);
        request.setLongitude(-93.0);

        User driver = new User();
        driver.setId(1L);
        User dispatcher = new User();
        dispatcher.setId(2L);
        Hospital hospital = new Hospital();
        hospital.setId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(userRepository.findById(2L)).thenReturn(Optional.of(dispatcher));
        when(hospitalRepository.findById(3L)).thenReturn(Optional.of(hospital));

        Mission savedMission = new Mission();
        savedMission.setId(1L);
        savedMission.setDriver(driver);
        savedMission.setDispatcher(dispatcher);
        savedMission.setHospital(hospital);
        savedMission.setLatitude(45.0);
        savedMission.setLongitude(-93.0);
        savedMission.setStatus(MissionStatus.PICKUP);

        when(missionRepository.save(any(Mission.class))).thenReturn(savedMission);

        Mission result = missionService.createMission(request);

        assertNotNull(result);
        assertEquals(45.0, result.getLatitude());
        assertEquals(-93.0, result.getLongitude());
        assertEquals(MissionStatus.PICKUP, result.getStatus());
        verify(missionRepository).save(any(Mission.class));
    }
}
