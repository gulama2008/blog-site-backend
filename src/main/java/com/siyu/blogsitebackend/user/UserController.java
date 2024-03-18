package com.siyu.blogsitebackend.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siyu.blogsitebackend.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> allUsers = this.userService.getAll();
        return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> found = this.userService.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<User>(found.get(), HttpStatus.OK);
        }
        throw new NotFoundException(String.format("User with id: %d does not exist", id));
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateDTO data) {
        User newUser = this.userService.createUser(data);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
