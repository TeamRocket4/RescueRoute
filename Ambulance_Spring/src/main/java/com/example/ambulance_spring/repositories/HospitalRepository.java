package com.example.ambulance_spring.repositories;

import com.example.ambulance_spring.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "hospitals", path = "hospitals")
@CrossOrigin()
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
