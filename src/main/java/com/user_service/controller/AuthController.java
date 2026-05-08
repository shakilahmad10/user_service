package com.user_service.controller;

import com.user_service.dto.LoginResponse;
import com.user_service.dto.UserLoginRequest;
import com.user_service.dto.UserRegisterRequest;
import com.user_service.dto.UserResponse;
import com.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        log.info("Received registration request for email: {}", request.getEmail());
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        log.info("Received login request for email: {}", request.getEmail());
        return ResponseEntity.ok(userService.login(request));
    }
}