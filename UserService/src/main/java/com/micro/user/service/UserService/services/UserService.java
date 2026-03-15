package com.micro.user.service.UserService.services;

import com.micro.user.service.UserService.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.SimpleTimeZone;


public interface UserService {

    ResponseEntity<User> saveUser(User user);

    List<User> getAllUsers();

    User getUserById(String userId);

    ResponseEntity<User> updateUser(User user,String userId);

    ResponseEntity<User> deleteUser(String userId);
}
