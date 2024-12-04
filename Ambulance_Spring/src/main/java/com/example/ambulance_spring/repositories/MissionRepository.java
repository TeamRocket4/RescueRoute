package com.example.ambulance_spring.repositories;

import com.example.ambulance_spring.entities.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "missions", path = "missions")
@CrossOrigin()
public interface MissionRepository extends JpaRepository<Mission, Long> {
}
