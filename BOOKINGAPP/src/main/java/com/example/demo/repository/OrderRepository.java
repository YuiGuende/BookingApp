package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.reception.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
    Order findByBooking(Booking booking);
}
