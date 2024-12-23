package com.example.ambulance_spring.services;

import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.entities.enums.Role;
import com.example.ambulance_spring.entities.enums.Status;
import com.example.ambulance_spring.repositories.UserRepository;
import com.example.ambulance_spring.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testClockin() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setStatus(Status.OUTOFSERVICE);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.clockin(1L);

        // Assert
        assertNotNull(result);
        assertEquals(Status.STANDBY, result.getStatus());
        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    void testClockout() {
        // Arrange
        User user = new User();
        user.setId(2L);
        user.setStatus(Status.STANDBY);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.clockout(2L);

        // Assert
        assertNotNull(result);
        assertEquals(Status.OUTOFSERVICE, result.getStatus());
        verify(userRepository).findById(2L);
        verify(userRepository).save(user);
    }

    @Test
    void testGetDrivers() {
        // Arrange
        User driver1 = new User();
        driver1.setId(1L);
        driver1.setRole(Role.DRIVER);

        User driver2 = new User();
        driver2.setId(2L);
        driver2.setRole(Role.DRIVER);

        when(userRepository.findByRole(Role.DRIVER)).thenReturn(Arrays.asList(driver1, driver2));

        // Act
        List<User> result = userService.getDrivers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository).findByRole(Role.DRIVER);
    }
}
