package com.example.demo.service;

import java.util.List;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.room.Room;
import com.example.demo.model.user.staff.Staff;

public interface StaffServiceInterface {

    void confirmBooking(Booking booking);

    List<Room> getRooms(Long staffId);

    Staff getCurrentsStaff(String username, String password);

    Staff getStaffByID(Long id);

    void checkIn(Booking booking);

    void checkOut(Payment payment);

    void cancel(Booking booking);
}
