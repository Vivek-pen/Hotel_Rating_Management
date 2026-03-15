package com.micro.user.service.UserService.controllers;

import com.micro.user.service.UserService.entities.User;
import com.micro.user.service.UserService.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserServiceImpl userServiceImpl;

    @GetMapping
    public List<User> getAllUsers(){
        return userServiceImpl.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUSer(@RequestBody User user){
        userServiceImpl.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId){
        User user = userServiceImpl.getUserById(userId);
        return user;
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String userId){
        userServiceImpl.updateUser(user,userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@PathVariable String userId){
        userServiceImpl.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
