package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.room.Room;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByRoom(Room room);
}
