package com.example.demo.Service;

import com.example.demo.DTOs.UserDTOs.UserRequestDTO;
import com.example.demo.DTOs.UserDTOs.UserResponseDTO;
import com.example.demo.Entity.User;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO register(UserRequestDTO dto);
    UserResponseDTO login(UserRequestDTO dto);
    void deleteById(Long id);
    UserResponseDTO updateUser(UserRequestDTO dto, Long id);
}
