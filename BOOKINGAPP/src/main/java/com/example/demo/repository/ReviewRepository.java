package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT distinct r, h, b FROM Review r "
            + "JOIN r.booking b "
            + "JOIN BookingRoom br ON br.booking = b "
            + "JOIN Room room ON br.room = room "
            + "JOIN room.hotel h "
            + "WHERE r.customer.id = :customerId "
            + "AND (r.rating <> 0 OR r.comment IS NOT NULL)")
    List<Object[]> findReviewedByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT distinct r, h, b FROM Review r "
            + "JOIN r.booking b "
            + "JOIN BookingRoom br ON br.booking = b "
            + "JOIN Room room ON br.room = room "
            + "JOIN room.hotel h "
            + "WHERE r.customer.id = :customerId "
            + "AND r.rating = 0 AND r.comment IS NULL")
    List<Object[]> findUnreviewedByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT r.customer, r FROM Review r "
            + "JOIN r.booking b "
            + "JOIN BookingRoom br ON br.booking = b "
            + "JOIN Room room ON br.room = room "
            + "WHERE room.hotel.id = :hotelId")
    List<Object[]> findReviewsByHotelId(@Param("hotelId") Long hotelId);

}
