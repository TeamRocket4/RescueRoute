package com.example.ambulance_spring.repositories;

import com.example.ambulance_spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@CrossOrigin()
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
