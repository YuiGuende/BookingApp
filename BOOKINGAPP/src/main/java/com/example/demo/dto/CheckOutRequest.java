package com.example.demo.dto;

import java.util.Map;

import com.example.demo.model.hotel.HotelService;
import com.example.demo.model.payment.Payment;

public record CheckOutRequest(Map<HotelService, Integer> services, Payment payment) {

}
