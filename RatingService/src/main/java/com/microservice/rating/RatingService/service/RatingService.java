package com.microservice.rating.RatingService.service;


import com.microservice.rating.RatingService.entities.Rating;
import org.apache.catalina.User;

import java.util.List;

public interface RatingService {

    Rating createRating(Rating rating);

    List<Rating> getRatings();

    List<Rating> getRatingsByUserId(String userId);

    List<Rating> getRatingsByHotelId(String hotelId);

}
