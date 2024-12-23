package com.example.ambulance_spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AmbulanceSpringApplicationTests {

	@Test
	void contextLoads() {
		// Test to ensure the application context loads successfully
	}
}
