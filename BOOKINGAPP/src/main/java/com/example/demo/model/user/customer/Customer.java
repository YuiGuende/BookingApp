package com.example.demo.model.user.customer;

import java.util.List;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Customer extends User {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")
    private List<Booking> bookings; // Danh sách đặt phòng của khách

    public Customer() {
    }

    public Customer(List<Booking> bookings, String name, String email, String username, String password, String phone) {
        super(name, email, username, password, phone);
        this.bookings = bookings;
    }

    public Customer(List<Booking> bookings, String email, String name, String phone) {
        super(email, name, phone);
        this.bookings = bookings;
    }


    @Override
    public String getRole() {
        return "User";
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
