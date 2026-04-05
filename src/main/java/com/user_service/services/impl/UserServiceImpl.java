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
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponse registerUser(UserRegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setName(savedUser.getName());

        return userResponse;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User does not exists"));
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    @Override
    public LoginResponse login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(
                ()->new UserNotFoundException("User not found"));

       if(!user.getPassword().equals(userLoginRequest.getPassword())){
           throw new InvalidCredentialsException("Invalid email or password");
       }

       LoginResponse loginResponse = new LoginResponse();
       loginResponse.setToken(jwtUtil.generateToken(user.getEmail(), user.getRole().name()));
       return loginResponse;
    }

    @Override
    public void UpdateUser(Long userId, UserRegisterRequest request) {

    }
}
