package com.azid.auth.backend.AZ.Auth.model;

public class AuthResponse {
    private String token;
    private String email;
    private String username;

    // Constructor
    public AuthResponse(String token, String email, String username) {
        this.token = token;
        this.email = email;
        this.username = username;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
