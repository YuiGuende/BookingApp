package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.user.customer.Customer;

@Repository
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

    Optional<Booking> findBookingById(Long id);

    // Thành

    @Query("SELECT DISTINCT b FROM Booking b "
            + "JOIN BookingRoom br ON br.booking = b "
            + "JOIN Room r ON br.room = r "
            + "WHERE r.hotel.id = :hotelId AND b.checked = :checked")
    List<Booking> findByHotelIdAndIsChecked(@Param("hotelId") Long hotelId, @Param("checked") boolean checked);


}
