package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.RegistrationLoginRequest;
import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.enums.Role;
import com.example.taskmanager.exceptions.BadRequestException;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<?> register(RegistrationLoginRequest registrationLoginRequest) {
        if (userRepository.findByEmail(registrationLoginRequest.getEmail()).isPresent()) {
            log.warn("registration failed: email exists");
            // be vague on purpose
            throw new BadRequestException("Failed to register");
        }

        User user = new User();
        user.setEmail(registrationLoginRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registrationLoginRequest.getPassword()));

        if (registrationLoginRequest.getRole() == null) {
            user.setRole(Role.USER);
        } else if (registrationLoginRequest.getRole().equals(Role.ADMIN)) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

        User savedUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setRole(savedUser.getRole());

        return new ApiResponse<>(201, "User registered successfully", userDTO);
    }

    @Override
    public ApiResponse<?> login(RegistrationLoginRequest registrationLoginRequest, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registrationLoginRequest.getEmail(),
                        registrationLoginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return new ApiResponse<>(200, "login success", null);
    }
}
