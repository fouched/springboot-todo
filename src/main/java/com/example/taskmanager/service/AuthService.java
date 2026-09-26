package com.example.taskmanager.service;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.RegistrationLoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    ApiResponse<?> register(RegistrationLoginRequest registrationLoginRequest);
    ApiResponse<?> login(RegistrationLoginRequest registrationLoginRequest, HttpServletRequest httpServletRequest);
}
