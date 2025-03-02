package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.user.customer.Customer;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT h.name, a.fullAddress, h.images, "
            + "b.id, b.checkInDate, b.checkOutDate, b.totalPrice, b.status "
            + "FROM Booking b "
            + "JOIN BookingRoom br ON b.id = br.booking.id "
            + "JOIN Room r ON br.room.id = r.id "
            + "JOIN Hotel h ON r.hotel.id = h.id "
            + "JOIN Address a ON h.address.id = a.id "
            + "WHERE b.customer = :customer")
    List<Object[]> findBookingsByCustomer(@Param("customer") Customer customer);

}
