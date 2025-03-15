package com.example.demo.model.reception;


import java.time.LocalDateTime;
import java.util.Map;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.hotel.HotelService;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
@Entity
@Table(name = "customer_order")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "booking_id")
    private Booking booking;
    private LocalDateTime checkInTime;
    private String indentity;
    private LocalDateTime checkOutTime;
   
    public Order() {
    }
    public Order(Booking booking, LocalDateTime checkInTime, String indentity, LocalDateTime checkOutTime) {
        this.booking = booking;
        this.checkInTime = checkInTime;
        this.indentity = indentity;
        this.checkOutTime = checkOutTime;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }
    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }
    public String getIndentity() {
        return indentity;
    }
    public void setIndentity(String indentity) {
        this.indentity = indentity;
    }
    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }
    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    
}
