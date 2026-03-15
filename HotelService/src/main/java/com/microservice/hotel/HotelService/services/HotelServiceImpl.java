package com.microservice.hotel.HotelService.services;

import com.microservice.hotel.HotelService.entities.Hotel;
import com.microservice.hotel.HotelService.exception.ResourceNotFoundException;
import com.microservice.hotel.HotelService.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel createHotel(Hotel hotel) {
        hotel.setId(UUID.randomUUID().toString());
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String id) {
        return hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("hotel with given id not found!!"));
    }

    @Override
    public ResponseEntity<Hotel> deleteHotel(String id) {
        hotelRepository.deleteById(id);
        return new ResponseEntity<Hotel>(HttpStatus.OK);
    }


}
