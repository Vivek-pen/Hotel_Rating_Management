package com.microservice.hotel.HotelService.services;

import com.microservice.hotel.HotelService.entities.Hotel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HotelService {

     Hotel createHotel(Hotel hotel);

     List<Hotel> getAllHotels();

     Hotel getHotel(String id);

     ResponseEntity<Hotel> deleteHotel(String id);

}
