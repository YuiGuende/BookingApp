package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.HotelDistanceDTO;
import com.example.demo.dto.HotelWithRoomsDTO;
import com.example.demo.dto.RoomDTO;
import com.example.demo.model.hotel.Hotel;

public interface HotelServiceInterface {

    List<Hotel> getHotels();

    Hotel getHotelByHotelId(Long id);

    HotelWithRoomsDTO getHotelDTOById(Long id,
    int roomQuantity,
    int adultQuantity,
    int childrenQuantity,
    LocalDate checkinDate,
    LocalDate checkoutDate);

    void addHotel(Hotel hotel);

    boolean isAddressExist(Hotel hotel);

    List<HotelDistanceDTO> getHotelDistanceDTOByAddress(
            int roomQuantity,
            String fullAddress,
            int adultQuantity,
            int childrenQuantity,
            LocalDate checkinDate,
            LocalDate checkoutDate);

    public List<RoomDTO> getAvailableRoomsDTO(
            int roomQuantity,
            Long hotelId,
            int adultQuantity,
            int childrenQuantity,
            LocalDate checkinDate,
            LocalDate checkoutDate);

    void sort(List<HotelDistanceDTO> dtos);

    void updateHotelDetails(Hotel hotel);
}
