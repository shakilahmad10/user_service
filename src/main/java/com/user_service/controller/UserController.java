package com.user_service.controller;

import com.user_service.dto.LoginResponse;
import com.user_service.dto.UserLoginRequest;
import com.user_service.dto.UserRegisterRequest;
import com.user_service.dto.UserResponse;
import com.user_service.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest request){
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long userId){
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest request){
        LoginResponse loginResponse = userService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    

}
