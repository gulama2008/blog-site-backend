package com.siyu.blogsitebackend.user;

import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siyu.blogsitebackend.auth.RegisterDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public Optional<User> getById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser;
    }
    
    public User createUser(RegisterDTO data) {
        String username = data.getUsername();
        String password = data.getPassword();
        User newUser = new User(username,password);
        User created = this.userRepository.save(newUser);
        return created;
    }

    public User getByUsername(String username) {
       return this.userRepository.findByUsername(username).orElse(null);
    }
    
}
