package com.example.demo.dto.order;

import java.time.LocalDateTime;
import java.util.Map;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.hotel.HotelService;

public class OrderDTO {

    private Long bookingId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Map<HotelService, Integer> services;
    private double roomsPrice;
    private double servicePrice;
    public OrderDTO(Long bookingId, LocalDateTime checkInTime, LocalDateTime checkOutTime,
            Map<HotelService, Integer> services, double roomsPrice, double servicePrice) {
        this.bookingId = bookingId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.services = services;
        this.roomsPrice = roomsPrice;
        this.servicePrice = servicePrice;
    }
    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }
    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }
    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }
    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    public Map<HotelService, Integer> getServices() {
        return services;
    }
    public void setServices(Map<HotelService, Integer> services) {
        this.services = services;
    }
    public double getRoomsPrice() {
        return roomsPrice;
    }
    public void setRoomsPrice(double roomsPrice) {
        this.roomsPrice = roomsPrice;
    }
    public double getServicePrice() {
        return servicePrice;
    }
    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }
    
}
