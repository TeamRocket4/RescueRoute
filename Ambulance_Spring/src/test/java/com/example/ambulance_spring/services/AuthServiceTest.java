package com.example.ambulance_spring.services;

import com.example.ambulance_spring.dto.LoginRequest;
import com.example.ambulance_spring.dto.LoginResponse;
import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.repositories.UserRepository;
import com.example.ambulance_spring.services.impl.AuthServiceImpl;
import com.example.ambulance_spring.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        boolean result = authService.register(user);

        // Assert
        assertTrue(result, "Registration should succeed");
        verify(userRepository).findByEmail(user.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterFailure_EmailAlreadyExists() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(new User());

        // Act
        boolean result = authService.register(user);

        // Assert
        assertFalse(result, "Registration should fail if email already exists");
        verify(userRepository).findByEmail(user.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(jwtUtils.generateToken(email)).thenReturn("jwtToken");

        // Act
        LoginResponse response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getAuthToken());
        assertEquals(email, response.getEmail());
        assertEquals("John", response.getFirstname());
        assertEquals("Doe", response.getLastname());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(email);
        verify(jwtUtils).generateToken(email);
    }


}
