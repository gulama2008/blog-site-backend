package com.siyu.blogsitebackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.siyu.blogsitebackend.jwt.JwtService;
import com.siyu.blogsitebackend.user.User;
import com.siyu.blogsitebackend.user.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

@Autowired
private UserService userService;

@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private JwtService jwtService;
    
    public AuthResponse register(RegisterDTO data) {
        String encodedPassword = this.passwordEncoder.encode(data.getPassword());
        data.setPassword(encodedPassword);
        User newUser = userService.createUser(data);
        String token = this.jwtService.generateToken(newUser);
        return new AuthResponse(token);
    } 
}
