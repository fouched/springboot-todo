package com.example.taskmanager.dto;

import com.example.taskmanager.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationLoginRequest {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private Role role;
}
