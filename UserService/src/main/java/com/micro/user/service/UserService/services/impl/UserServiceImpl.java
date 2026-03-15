package com.micro.user.service.UserService.services.impl;

import com.micro.user.service.UserService.entities.User;
import com.micro.user.service.UserService.exceptions.ResourceNotFound;
import com.micro.user.service.UserService.repositories.UserRepo;
import com.micro.user.service.UserService.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.transform.OutputKeys;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<User> saveUser(User user) {
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(String userId) {
        return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User with given Id is not found on server!!"+userId));
    }

    @Override
    public ResponseEntity<User> updateUser(User user,String userId) {
        User user1 = getUserById(userId);
        user1.setName(user.getName());
        user1.setAbout(user.getAbout());
        user1.setEmail(user.getEmail());
        userRepo.save(user1);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<User> deleteUser(String userId) {
        return null;
    }
}
