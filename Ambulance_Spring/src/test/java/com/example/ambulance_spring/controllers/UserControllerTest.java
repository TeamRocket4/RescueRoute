package com.example.ambulance_spring.controllers;

import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import com.example.ambulance_spring.entities.enums.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void clockout_shouldReturnStatus() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setStatus(Status.OUTOFSERVICE); // Use an enum value here
        when(userService.clockout(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<String> response = userController.clockout(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("OUTOFSERVICE", response.getBody()); // Verify the string representation of the enum
        verify(userService, times(1)).clockout(userId);
    }

    @Test
    void clockin_shouldReturnStatus() {
        // Arrange
        Long userId = 2L;
        User mockUser = new User();
        mockUser.setStatus(Status.STANDBY);
        when(userService.clockin(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<String> response = userController.clockin(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("STANDBY", response.getBody());
        verify(userService, times(1)).clockin(userId);
    }


    @Test
    void getDrivers_shouldReturnListOfDrivers() {
        User driver1 = new User();
        driver1.setId(1L);
        User driver2 = new User();
        driver2.setId(2L);
        List<User> mockDrivers = List.of(driver1, driver2);

        when(userService.getDrivers()).thenReturn(mockDrivers);

        ResponseEntity<List<User>> response = userController.getDrivers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getDrivers();
    }
}
