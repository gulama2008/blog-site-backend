package com.siyu.blogsitebackend.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    
}
