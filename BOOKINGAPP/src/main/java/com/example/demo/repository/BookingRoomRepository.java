package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingRoom;
import com.example.demo.model.room.Room;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Long> {
    List<BookingRoom> findBookingRoomByRoom(Room room);
    List<Room> findRoomByBooking(Booking booking);
}
