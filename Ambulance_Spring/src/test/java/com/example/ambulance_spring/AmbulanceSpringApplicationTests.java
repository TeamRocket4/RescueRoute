package com.example.ambulance_spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import com.example.ambulance_spring.repositories.UserRepository;
import com.example.ambulance_spring.utils.JwtUtils;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class AmbulanceSpringApplicationTests {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private AuthenticationManager authenticationManager;

	@MockBean
	private JwtUtils jwtUtils;
	@Test
	void contextLoads() {
		// Test to ensure the application context loads successfully
	}
}
