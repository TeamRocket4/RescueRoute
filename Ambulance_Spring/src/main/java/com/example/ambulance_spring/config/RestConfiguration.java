package com.example.ambulance_spring.config;

import com.example.ambulance_spring.entities.Hospital;
import com.example.ambulance_spring.entities.Mission;
import com.example.ambulance_spring.entities.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Hospital.class);
        config.exposeIdsFor(Mission.class);
    }
}
