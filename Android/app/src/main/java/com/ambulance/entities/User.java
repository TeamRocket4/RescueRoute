package com.ambulance.entities;

import androidx.annotation.NonNull;

public class User {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String authToken;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return authToken;
    }

    public void setToken(String token) {
        this.authToken = token;
    }

    @NonNull
    @Override
    public String toString() {
        return
                id+"/"+firstname+"/"+lastname+"/"+email+"/"+authToken;
    }
}
