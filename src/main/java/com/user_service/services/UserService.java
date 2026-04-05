package com.user_service.services;

import com.user_service.dto.LoginResponse;
import com.user_service.dto.UserLoginRequest;
import com.user_service.dto.UserRegisterRequest;
import com.user_service.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRegisterRequest request);
    UserResponse getUserById(Long userId);
    LoginResponse login(UserLoginRequest userLoginRequest);
    void UpdateUser(Long userId, UserRegisterRequest request);
}
