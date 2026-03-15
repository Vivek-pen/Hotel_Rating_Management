package com.microservice.hotel.HotelService.controllers;

import com.microservice.hotel.HotelService.entities.Hotel;
import com.microservice.hotel.HotelService.repositories.HotelRepository;
import com.microservice.hotel.HotelService.services.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelServiceImpl hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        hotelService.createHotel(hotel);
        return new ResponseEntity<Hotel>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<Hotel> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable String id){
        return hotelService.getHotel(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Hotel> deleteHotel(@PathVariable String id){
        return hotelService.deleteHotel(id);
    }
}
