package com.micro.user.service.UserService.services.impl;

import com.micro.user.service.UserService.entities.Hotel;
import com.micro.user.service.UserService.entities.Rating;
import com.micro.user.service.UserService.entities.User;
import com.micro.user.service.UserService.exceptions.ResourceNotFound;
import com.micro.user.service.UserService.external.services.HotelService;
import com.micro.user.service.UserService.repositories.UserRepo;
import com.micro.user.service.UserService.services.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.OutputKeys;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

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
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public User getUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User with given Id is not found on server!!"+userId));

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("Ratings received: {}", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating>ratingList = ratings.stream().map(rating -> {

            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            logger.info("response status code: {}",forEntity.getStatusCode());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }

    //creating fallbackmethod for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("Dummy because service is down")
                .userId("141234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
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
