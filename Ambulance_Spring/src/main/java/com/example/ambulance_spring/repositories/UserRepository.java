package com.example.ambulance_spring.repositories;

import com.example.ambulance_spring.entities.User;
import com.example.ambulance_spring.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@CrossOrigin()
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByRole(Role role);
}
