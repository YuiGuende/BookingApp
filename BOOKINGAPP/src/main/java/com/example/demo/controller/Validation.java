package com.example.demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.example.demo.dto.hotel.HotelSearchInforDTO;

public class Validation {
    public static  void validateHotelSearchInforDTO(HotelSearchInforDTO hotelSearchInforDTO) {
        if(hotelSearchInforDTO.getCheckInDate().isAfter(hotelSearchInforDTO.getCheckOutDate())){
            throw new IllegalArgumentException("checkin date must before checkout date");
        }
        if(hotelSearchInforDTO.getCheckInDate().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("checkin date must after today");
        }
        if(ChronoUnit.DAYS.between(hotelSearchInforDTO.getCheckInDate(), hotelSearchInforDTO.getCheckOutDate())>=90){
            throw new IllegalArgumentException("Can not book more than 90 days");
        }
    }
}
