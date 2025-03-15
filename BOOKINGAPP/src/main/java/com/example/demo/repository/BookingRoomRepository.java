package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingRoom;
import com.example.demo.model.room.Room;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Long> {
    List<BookingRoom> findBookingRoomByRoom(Room room);
     @Query("SELECT br.room FROM BookingRoom br WHERE br.booking = :booking")
    List<Room> findRoomByBooking(@Param("booking") Booking booking);
    List<BookingRoom> findByBooking(Booking booking);
    
}
