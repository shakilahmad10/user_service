package com.user_service.controller;


import com.user_service.dto.UserRegisterRequest;
import com.user_service.dto.UserResponse;
import com.user_service.services.UserService;
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

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long userId){
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRegisterRequest request){
        UserResponse response = userService.UpdateUser(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
