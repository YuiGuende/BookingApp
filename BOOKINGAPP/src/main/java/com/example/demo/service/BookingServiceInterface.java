package com.example.demo.service;

import java.util.List;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingStatus;

public interface BookingServiceInterface {

    List<Booking> getBookingList();

    List<Booking> getBookingListByHotelId(Long id);

    void saveBooking(Booking booking);

    void updateBookingStatus(Booking booking, BookingStatus status);

    void validateBooking(Booking booking);

}
