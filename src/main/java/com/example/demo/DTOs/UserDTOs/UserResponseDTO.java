package com.example.demo.DTOs.UserDTOs;

import com.example.demo.Entity.User;

public class UserResponseDTO {
    private Long id;
    private String email;
    private String password;
    private String role;
    private String token;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public UserResponseDTO(User user, String token) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.role = user.getRole();
            this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}
