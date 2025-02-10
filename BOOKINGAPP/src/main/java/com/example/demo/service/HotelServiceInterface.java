package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.HotelDistanceDTO;
import com.example.demo.dto.HotelWithRoomsDTO;
import com.example.demo.model.address.Address;
import com.example.demo.model.hotel.Hotel;

public interface HotelServiceInterface {

    List<Hotel> getHotels();

    Hotel getHotelByHotelId(Long id);

    void addHotel(Hotel hotel);

    boolean isAddressExist(Hotel hotel);

    List<HotelDistanceDTO> getHotelDistanceDTOByAddress(
            Address address,
            int capacity,
            LocalDate checkinDate,
            LocalDate checkoutDate);

    public HotelWithRoomsDTO getHotelWithAvailableRoomsDTO(
        Long id, 
        int capacity, 
        LocalDate checkinDate,
        LocalDate checkoutDate);

    void sort(List<HotelDistanceDTO> dtos);

}
