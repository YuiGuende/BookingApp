package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.user.staff.BookingForStaff;

@Repository
public interface BookingForStaffRepository extends JpaRepository<BookingForStaff, Long> {
    Optional<BookingForStaff> findByHotelId(Long hotelId);
}