package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.hotel.HotelService;
import com.example.demo.model.payment.Payment;

import jakarta.transaction.Transactional;

public interface OrderServiceInterface {

    void checkIn(Long bookingId, String indentity);

    //dto
    OrderDTO checkOut(Long bookingId, Map<HotelService, Integer> services, Payment payment);

    List<HotelService> getHotelServiceList(Long hotelId);
}
