package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BookingDetaislDTO;
import com.example.demo.dto.BookingRequiredmentDTO;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingStatus;

public interface BookingServiceInterface {

    List<Booking> getBookingList();

    List<Booking> getBookingListByHotelId(Long id);
    Booking findBookingById(Long id);

    void saveBooking(BookingRequiredmentDTO bookingRequiredmentDTO);

    void updateBookingStatus(Booking booking, BookingStatus status);

    Booking validateBooking(BookingRequiredmentDTO bookingRequiredmentDTO);

    List<BookingDetaislDTO> getBookingDetaislDTOs(String email, String phone);

}
