package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.exceptions.NotFoundException;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;

    @Override
    public ApiResponse<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream()
                .map(User::toDTO)
                .toList();

        return new ApiResponse<>(200, "Users retrieved successfully", users);
    }

    @Override
    public ApiResponse<UserDTO> getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return new ApiResponse<>(200, "User retrieved successfully", user.toDTO());
    }

    @Override
    public ApiResponse<UserDTO> updateUser(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }

        User updatedUser = userRepository.save(user);
        return new ApiResponse<>(200, "User updated successfully", updatedUser.toDTO());
    }
}
