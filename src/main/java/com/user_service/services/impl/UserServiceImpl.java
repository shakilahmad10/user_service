package com.user_service.services.impl;

import com.user_service.config.JwtUtil;
import com.user_service.dto.LoginResponse;
import com.user_service.dto.UserLoginRequest;
import com.user_service.dto.UserRegisterRequest;
import com.user_service.dto.UserResponse;
import com.user_service.entity.Role;
import com.user_service.entity.User;
import com.user_service.exception.InvalidCredentialsException;
import com.user_service.exception.UserNotFoundException;
import com.user_service.repository.UserRepository;
import com.user_service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponse registerUser(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed: Email {} already exists", request.getEmail());
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setName(savedUser.getName());

        return userResponse;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exists"));
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    @Override
    public LoginResponse login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        if (!user.getPassword().equals(userLoginRequest.getPassword())) {
            log.error("Login failed for email: {} - Invalid password", userLoginRequest.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        log.info("User {} logged in successfully", userLoginRequest.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtUtil.generateToken(user.getEmail(), user.getRole().name()));
        return loginResponse;
    }

    @Override
    public UserResponse UpdateUser(Long userId, UserRegisterRequest request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exits."));

        existingUser.setName(request.getName());

        User savedUser = userRepository.save(existingUser);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setName(savedUser.getName());
        userResponse.setEmail(savedUser.getEmail());
        return userResponse;
    }
}
