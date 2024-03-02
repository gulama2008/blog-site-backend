package com.siyu.blogsitebackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@Autowired
    private AuthenticationManager authenticationManager;
    
public AuthResponse register(RegisterDTO data) {
    String encodedPassword = this.passwordEncoder.encode(data.getPassword());
    data.setPassword(encodedPassword);
    User newUser = userService.createUser(data);
    String token = this.jwtService.generateToken(newUser);
    return new AuthResponse(token);
}
    
public AuthResponse login(LoginDTO data) {
        UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());

        authenticationManager.authenticate(userPassToken);

        User user = this.userService.getByUsername(data.getUsername());

        if(user == null) {
            return null;
        }

        String token = this.jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
